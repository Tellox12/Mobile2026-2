package com.example.asistencia.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import java.util.function.BiConsumer;

/**
 * Adaptador genérico reutilizable para cualquier tipo T y cualquier ViewBinding B.
 *
 * Ejemplo de uso en un fragmento:
 * <pre>
 * GenericAdapter<Grupo, ItemGrupoBinding> adapter = new GenericAdapter<>(
 *     DiffCallbacks.GRUPO,
 *     (inflater, parent) -> ItemGrupoBinding.inflate(inflater, parent, false),
 *     (binding, grupo)   -> binding.tvNombre.setText(grupo.nombre),
 *     (binding, grupo)   -> binding.getRoot().setOnClickListener(v -> onClick(grupo))
 * );
 * </pre>
 */
public class GenericAdapter<T, B extends ViewBinding>
        extends ListAdapter<T, GenericAdapter.GenericViewHolder<B>> {

    public interface BindingInflater<B extends ViewBinding> {
        B inflate(LayoutInflater inflater, ViewGroup parent);
    }

    private final BindingInflater<B> inflater;
    private final BiConsumer<B, T>   binder;
    private final BiConsumer<B, T>   clickBinder;

    public GenericAdapter(
            @NonNull DiffUtil.ItemCallback<T> diffCallback,
            @NonNull BindingInflater<B>       inflater,
            @NonNull BiConsumer<B, T>         binder,
            @NonNull BiConsumer<B, T>         clickBinder) {
        super(diffCallback);
        this.inflater    = inflater;
        this.binder      = binder;
        this.clickBinder = clickBinder;
    }

    @NonNull @Override
    public GenericViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = inflater.inflate(LayoutInflater.from(parent.getContext()), parent);
        return new GenericViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder<B> holder, int position) {
        T item = getItem(position);
        binder.accept(holder.binding, item);
        clickBinder.accept(holder.binding, item);
    }

    public static class GenericViewHolder<B extends ViewBinding>
            extends RecyclerView.ViewHolder {
        public final B binding;
        public GenericViewHolder(B b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
