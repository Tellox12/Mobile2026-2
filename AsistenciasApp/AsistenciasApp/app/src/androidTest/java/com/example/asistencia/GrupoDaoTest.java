package com.example.asistencia;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.asistencia.data.AppDatabase;
import com.example.asistencia.data.dao.AlumnoDao;
import com.example.asistencia.data.dao.AsistenciaDao;
import com.example.asistencia.data.dao.GrupoDao;
import com.example.asistencia.data.entity.Alumno;
import com.example.asistencia.data.entity.AlumnoGrupoCrossRef;
import com.example.asistencia.data.entity.Asistencia;
import com.example.asistencia.data.entity.Grupo;
import com.example.asistencia.data.relation.AsistenciaDetalle;
import com.example.asistencia.data.relation.GrupoWithAlumnos;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GrupoDaoTest {

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    private AppDatabase  db;
    private GrupoDao     grupoDao;
    private AlumnoDao    alumnoDao;
    private AsistenciaDao asistenciaDao;

    @Before
    public void setUp() {
        Context ctx = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(ctx, AppDatabase.class)
                 .allowMainThreadQueries()
                 .build();
        grupoDao      = db.grupoDao();
        alumnoDao     = db.alumnoDao();
        asistenciaDao = db.asistenciaDao();
    }

    @After
    public void tearDown() { db.close(); }

    @Test
    public void insertGrupo_getAll_returnsInsertedGrupo() throws InterruptedException {
        grupoDao.insert(new Grupo("Matemáticas", "Desc", "Lun 08:00"));
        List<Grupo> result = LiveDataTestUtil.getValue(grupoDao.getAll());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Matemáticas", result.get(0).nombre);
    }

    @Test
    public void deleteGrupo_removesFromDB() throws InterruptedException {
        Grupo g = new Grupo("Física", "Mecánica", "Mar 09:00");
        long id = grupoDao.insert(g);
        g.id = id;
        grupoDao.delete(g);
        List<Grupo> result = LiveDataTestUtil.getValue(grupoDao.getAll());
        assertTrue(result.isEmpty());
    }

    @Test
    public void updateGrupo_persistsChanges() throws InterruptedException {
        Grupo g = new Grupo("Biología", "General", "Mié 11:00");
        long id = grupoDao.insert(g);
        g.id = id;
        g.nombre = "Biología Avanzada";
        grupoDao.update(g);
        List<Grupo> result = LiveDataTestUtil.getValue(grupoDao.getAll());
        assertEquals("Biología Avanzada", result.get(0).nombre);
    }

    @Test
    public void getGrupoWithAlumnos_returnsCorrectAlumnos() throws InterruptedException {
        long gId = grupoDao.insert(new Grupo("POO", "Programación OO", "Jue 10:00"));
        long a1  = alumnoDao.insert(new Alumno("Ana",  "García", "ana@test.com"));
        long a2  = alumnoDao.insert(new Alumno("Luis", "Pérez",  "luis@test.com"));
        alumnoDao.insertCrossRef(new AlumnoGrupoCrossRef(a1, gId));
        alumnoDao.insertCrossRef(new AlumnoGrupoCrossRef(a2, gId));
        GrupoWithAlumnos gwa = LiveDataTestUtil.getValue(grupoDao.getGrupoWithAlumnos(gId));
        assertNotNull(gwa);
        assertEquals(2, gwa.alumnos.size());
    }

    @Test
    public void deleteCrossRef_removesAlumnoFromGrupo() throws InterruptedException {
        long gId = grupoDao.insert(new Grupo("Química", "Orgánica", "Vie 08:00"));
        long aId = alumnoDao.insert(new Alumno("Marta", "Ruiz", "marta@test.com"));
        alumnoDao.insertCrossRef(new AlumnoGrupoCrossRef(aId, gId));
        alumnoDao.deleteCrossRef(new AlumnoGrupoCrossRef(aId, gId));
        GrupoWithAlumnos gwa = LiveDataTestUtil.getValue(grupoDao.getGrupoWithAlumnos(gId));
        assertTrue(gwa.alumnos.isEmpty());
    }

    @Test
    public void insertAsistencia_countPresentes_returnsCorrectCount() throws InterruptedException {
        long gId = grupoDao.insert(new Grupo("Historia", "Universal", "Lun 14:00"));
        long aId = alumnoDao.insert(new Alumno("Pedro", "Salas", "pedro@test.com"));
        alumnoDao.insertCrossRef(new AlumnoGrupoCrossRef(aId, gId));
        asistenciaDao.insert(new Asistencia(aId, gId, "2025-04-01", true));
        asistenciaDao.insert(new Asistencia(aId, gId, "2025-04-03", true));
        asistenciaDao.insert(new Asistencia(aId, gId, "2025-04-05", false));
        Integer count = LiveDataTestUtil.getValue(asistenciaDao.countPresentes(aId, gId));
        assertEquals(Integer.valueOf(2), count);
    }

    @Test
    public void getHistorial_returnsJoinedDetails() throws InterruptedException {
        long gId = grupoDao.insert(new Grupo("Inglés", "Básico", "Mar 16:00"));
        long aId = alumnoDao.insert(new Alumno("Laura", "Vega", "laura@test.com"));
        alumnoDao.insertCrossRef(new AlumnoGrupoCrossRef(aId, gId));
        asistenciaDao.insert(new Asistencia(aId, gId, "2025-05-01", true));
        List<AsistenciaDetalle> historial =
                LiveDataTestUtil.getValue(asistenciaDao.getHistorial(aId, gId));
        assertNotNull(historial);
        assertEquals(1, historial.size());
        assertEquals("Laura",  historial.get(0).nombreAlumno);
        assertEquals("Inglés", historial.get(0).nombreGrupo);
        assertTrue(historial.get(0).asistencia.presente);
    }
}
