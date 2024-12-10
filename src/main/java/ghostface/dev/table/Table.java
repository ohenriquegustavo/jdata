package ghostface.dev.table;

import ghostface.dev.DataType;
import ghostface.dev.content.UnmodifiableContent;
import ghostface.dev.exception.NameAlreadyExistsException;
import ghostface.dev.exception.key.DuplicatedKeyValueException;
import ghostface.dev.exception.key.MissingKeyException;
import ghostface.dev.exception.table.TableStateException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;

public interface Table {

    @NotNull String getId();

    @NotNull Elements getElements();

    @NotNull Columns getColumns();

    // Classes

    interface Elements extends UnmodifiableContent<@NotNull Element> {

        @NotNull Table getTable();

        /**
         * @throws MissingKeyException If the key is missing
         * @throws DuplicatedKeyValueException If the key value already exists
         * @throws TableStateException If table#getColumns is empty
         * */
        @NotNull Element create(@NotNull TableData<?> @NotNull ... datas) throws MissingKeyException, DuplicatedKeyValueException, TableStateException;

        boolean remove(int index);

        @NotNull Optional<? extends Element> get(int index);

        @Override
        @Unmodifiable @NotNull Collection<@NotNull Element> toCollection();
    }

    interface Columns extends UnmodifiableContent<@NotNull Column<?>> {

        @NotNull Table getTable();

        /**
         * @throws NameAlreadyExistsException if name is already in use
         * */
        <E> @NotNull Column<E> create(@NotNull String name, @NotNull DataType<E> dataType, @Nullable E value, boolean isNullable) throws NameAlreadyExistsException;

        /**
         * @throws NameAlreadyExistsException if the name is already in use
         * @throws TableStateException if the table elements is not empty
         * */
        <E> @NotNull Column<E> createKey(@NotNull String name, @NotNull DataType<E> dataType) throws NameAlreadyExistsException, TableStateException;

        @NotNull Optional<? extends @NotNull Column<?>> get(@NotNull String name);

        boolean remove(@NotNull String columnId);

        @Override
        @Unmodifiable @NotNull Collection<@NotNull Column<?>> toCollection();
    }
}
