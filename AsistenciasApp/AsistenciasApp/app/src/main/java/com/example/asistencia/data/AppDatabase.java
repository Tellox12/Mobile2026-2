package com.example.asistencia.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.asistencia.data.dao.*;
import com.example.asistencia.data.entity.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities     = { Alumno.class, Grupo.class, AlumnoGrupoCrossRef.class, Asistencia.class },
    version      = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GrupoDao      grupoDao();
    public abstract AlumnoDao     alumnoDao();
    public abstract AsistenciaDao asistenciaDao();

    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(4);

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "asistencia_db")
                        .addCallback(seedCallback)
                        .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback seedCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            dbExecutor.execute(() -> {
                GrupoDao  gDao = INSTANCE.grupoDao();
                AlumnoDao aDao = INSTANCE.alumnoDao();

                long g1 = gDao.insert(new Grupo("Matemáticas I",  "Álgebra lineal",  "Lun-Mié 08:00"));
                long g2 = gDao.insert(new Grupo("Programación II","POO con Java",    "Mar-Jue 10:00"));
                long g3 = gDao.insert(new Grupo("Física General", "Mecánica clásica","Vie 07:00"));

                long a1 = aDao.insert(new Alumno("Ana",    "García",   "ana@mail.com"));
                long a2 = aDao.insert(new Alumno("Luis",   "Martínez", "luis@mail.com"));
                long a3 = aDao.insert(new Alumno("Sofía",  "Ramírez",  "sofia@mail.com"));
                long a4 = aDao.insert(new Alumno("Carlos", "López",    "carlos@mail.com"));

                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a1, g1));
                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a1, g2));
                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a2, g1));
                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a2, g3));
                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a3, g2));
                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a4, g1));
                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a4, g2));
                aDao.insertCrossRef(new AlumnoGrupoCrossRef(a4, g3));

                AsistenciaDao asDao = INSTANCE.asistenciaDao();
                asDao.insert(new Asistencia(a1, g1, "2025-04-28", true));
                asDao.insert(new Asistencia(a1, g1, "2025-04-30", false));
                asDao.insert(new Asistencia(a2, g1, "2025-04-28", true));
                asDao.insert(new Asistencia(a4, g2, "2025-04-29", true));
            });
        }
    };
}
