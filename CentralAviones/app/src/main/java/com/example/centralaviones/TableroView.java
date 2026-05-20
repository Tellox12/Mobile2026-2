package com.example.centralaviones;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;

public class TableroView extends View {

    // ─── Tamaño base de celda en dp ───────────────────────────────────────────
    private static final float BASE_CELL_DP = 50f;

    private float cellSize;          // cellSize en px = BASE_CELL_DP * density * scaleFactor
    private float scaleFactor = 1f;
    private float offsetX = 0f, offsetY = 0f;

    // ─── Gestos ───────────────────────────────────────────────────────────────
    private ScaleGestureDetector scaleDetector;
    private boolean isScaling = false;
    private float lastTouchX, lastTouchY;
    private int activePointerId = MotionEvent.INVALID_POINTER_ID;

    // ─── Datos del tablero ────────────────────────────────────────────────────
    private char[][] tablero;
    private int tamano = 20;
    private boolean centered = false;

    // ─── Paints ───────────────────────────────────────────────────────────────
    private final Paint pCellNormal   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pCellPlane    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pCellCollide  = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pBevelLight   = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pBevelDark    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pGrid         = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pPlane        = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pPlaneGlow    = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pCollide      = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pCollideGlow  = new Paint(Paint.ANTI_ALIAS_FLAG);

    // ─── Constructores ────────────────────────────────────────────────────────
    public TableroView(Context ctx) { super(ctx); init(ctx); }
    public TableroView(Context ctx, AttributeSet a) { super(ctx, a); init(ctx); }
    public TableroView(Context ctx, AttributeSet a, int d) { super(ctx, a, d); init(ctx); }

    private void init(Context ctx) {
        float dp = ctx.getResources().getDisplayMetrics().density;
        cellSize = BASE_CELL_DP * dp;

        pCellNormal.setColor(0xFF0D2137);
        pCellPlane .setColor(0xFF0F3050);
        pCellCollide.setColor(0xFF2D0812);

        pBevelLight.setColor(0xFF1B4D7A);
        pBevelDark .setColor(0xFF04090F);

        pGrid.setColor(0xFF133450);
        pGrid.setStyle(Paint.Style.STROKE);
        pGrid.setStrokeWidth(dp);

        // Plane: teal filled arrow
        pPlane.setColor(0xFF64FFDA);
        pPlane.setStyle(Paint.Style.FILL);

        // Plane glow: semi-transparent teal circle
        pPlaneGlow.setColor(0x2564FFDA);
        pPlaneGlow.setStyle(Paint.Style.FILL);

        // Collision: red X stroke
        pCollide.setColor(0xFFFF5252);
        pCollide.setStyle(Paint.Style.STROKE);
        pCollide.setStrokeCap(Paint.Cap.ROUND);

        // Collision glow: semi-transparent larger X
        pCollideGlow.setColor(0x40FF5252);
        pCollideGlow.setStyle(Paint.Style.STROKE);
        pCollideGlow.setStrokeCap(Paint.Cap.ROUND);

        // Hardware-accelerated layer (no BlurMaskFilter needed)
        setLayerType(LAYER_TYPE_HARDWARE, null);

        // ── Scale gesture ──────────────────────────────────────────────────────
        scaleDetector = new ScaleGestureDetector(ctx,
            new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector d) {
                    float newScale = Math.max(0.25f, Math.min(scaleFactor * d.getScaleFactor(), 5f));
                    // Zoom toward focus point
                    float fx = d.getFocusX(), fy = d.getFocusY();
                    offsetX = fx - (fx - offsetX) * (newScale / scaleFactor);
                    offsetY = fy - (fy - offsetY) * (newScale / scaleFactor);
                    scaleFactor = newScale;
                    isScaling = true;
                    invalidate();
                    return true;
                }
                @Override public void onScaleEnd(ScaleGestureDetector d) { isScaling = false; }
            });
    }

    // ─── API pública ──────────────────────────────────────────────────────────
    public void setTablero(char[][] tablero, int tamano) {
        this.tablero = tablero;
        this.tamano  = tamano;
        if (!centered && getWidth() > 0) { centerGrid(); centered = true; }
        invalidate();
    }

    private void centerGrid() {
        float total = tamano * cellSize * scaleFactor;
        offsetX = (getWidth()  - total) / 2f;
        offsetY = (getHeight() - total) / 2f;
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        if (!centered && tamano > 0) { centerGrid(); centered = true; }
    }

    // ─── Toque: pan + pinch-zoom ──────────────────────────────────────────────
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        scaleDetector.onTouchEvent(ev);
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                lastTouchX = ev.getX(); lastTouchY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isScaling && ev.getPointerCount() == 1) {
                    int idx = ev.findPointerIndex(activePointerId);
                    if (idx >= 0) {
                        offsetX += ev.getX(idx) - lastTouchX;
                        offsetY += ev.getY(idx) - lastTouchY;
                        lastTouchX = ev.getX(idx);
                        lastTouchY = ev.getY(idx);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP: {
                int pidx = ev.getActionIndex();
                if (ev.getPointerId(pidx) == activePointerId) {
                    int newIdx = (pidx == 0) ? 1 : 0;
                    lastTouchX = ev.getX(newIdx); lastTouchY = ev.getY(newIdx);
                    activePointerId = ev.getPointerId(newIdx);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                activePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
        }
        return true;
    }

    // ─── Dibujo ───────────────────────────────────────────────────────────────
    @Override
    protected void onDraw(Canvas canvas) {
        // Fondo oscuro
        canvas.drawColor(0xFF08111E);

        if (tablero == null) return;

        float cs   = cellSize * scaleFactor;   // tamaño celda en px
        float gap  = Math.max(1f, scaleFactor); // separación entre celdas
        float bev  = Math.max(1.5f, cs * 0.07f); // altura del bisel

        // Ajustar stroke de colisión al tamaño actual
        float strokeW = Math.max(2f, cs * 0.12f);
        pCollide    .setStrokeWidth(strokeW);
        pCollideGlow.setStrokeWidth(strokeW * 2.2f);

        for (int row = 0; row < tamano; row++) {
            for (int col = 0; col < tamano; col++) {

                float L = offsetX + col * cs;
                float T = offsetY + row * cs;
                float R = L + cs - gap;
                float B = T + cs - gap;

                // Culling: no dibujar celdas fuera de pantalla
                if (R < 0 || L > getWidth() || B < 0 || T > getHeight()) continue;

                char sym = tablero[row][col];

                // ── 1. Fondo de celda ─────────────────────────────────────────
                Paint bg = (sym == 'X') ? pCellCollide
                         : (sym != ' ') ? pCellPlane
                         : pCellNormal;
                canvas.drawRect(L, T, R, B, bg);

                // ── 2. Bisel 3D ───────────────────────────────────────────────
                // Aristas superiores/izquierdas → claras (luz desde arriba-izq)
                canvas.drawRect(L,       T,       R,       T + bev, pBevelLight); // top
                canvas.drawRect(L,       T,       L + bev, B,       pBevelLight); // left
                // Aristas inferiores/derechas → oscuras (sombra)
                canvas.drawRect(L,       B - bev, R,       B,       pBevelDark);  // bottom
                canvas.drawRect(R - bev, T,       R,       B,       pBevelDark);  // right

                // ── 3. Borde de grid ──────────────────────────────────────────
                canvas.drawRect(L, T, R, B, pGrid);

                // ── 4. Contenido ──────────────────────────────────────────────
                if (sym == 'X') {
                    drawCollision(canvas, L, T, R, B);
                } else if (sym != ' ') {
                    drawPlane(canvas, L, T, R, B, sym);
                }
            }
        }
    }

    // Avión: flecha apuntando en la dirección de movimiento
    private final Path planePath = new Path();
    private void drawPlane(Canvas canvas, float L, float T, float R, float B, char dir) {
        float cx = (L + R) / 2f, cy = (T + B) / 2f;
        float sz = (R - L) * 0.32f;

        // Glow circular detrás del avión
        float glowR = sz * 1.8f;
        canvas.drawCircle(cx, cy, glowR, pPlaneGlow);

        // Flecha (triángulo + cola pequeña)
        planePath.reset();
        planePath.moveTo(cx,          cy - sz);           // punta
        planePath.lineTo(cx - sz * 0.7f, cy + sz * 0.5f);// base izq
        planePath.lineTo(cx,          cy + sz * 0.1f);    // muesca centro
        planePath.lineTo(cx + sz * 0.7f, cy + sz * 0.5f);// base der
        planePath.close();

        canvas.save();
        canvas.rotate(dirToAngle(dir), cx, cy);
        canvas.drawPath(planePath, pPlane);
        canvas.restore();
    }

    // Colisión: X con glow
    private void drawCollision(Canvas canvas, float L, float T, float R, float B) {
        float m = (R - L) * 0.28f;
        float x1 = L + m, y1 = T + m, x2 = R - m, y2 = B - m;
        // Glow más grueso primero
        canvas.drawLine(x1, y1, x2, y2, pCollideGlow);
        canvas.drawLine(x2, y1, x1, y2, pCollideGlow);
        // X principal
        canvas.drawLine(x1, y1, x2, y2, pCollide);
        canvas.drawLine(x2, y1, x1, y2, pCollide);
    }

    private float dirToAngle(char dir) {
        switch (dir) {
            case '^': return 0f;
            case 'v': return 180f;
            case '<': return -90f;
            case '>': return 90f;
            default:  return 0f;
        }
    }
}
