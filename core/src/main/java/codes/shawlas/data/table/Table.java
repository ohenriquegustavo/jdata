package codes.shawlas.data.table;

import codes.shawlas.data.DataType;
import codes.shawlas.data.exception.column.ColumnAlreadyExistsException;
import codes.shawlas.data.exception.column.ColumnException;
import codes.shawlas.data.exception.table.NoEmptyTableException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Table {

    @NotNull String getName();

    @NotNull Elements getElements();

    @NotNull Columns getColumns();

    // Classes

    interface Elements {

        @NotNull Table getTable();

        /**
         * @throws ColumnException if any column errors occurs
         * */
        @NotNull Element create(@NotNull EntryData<?> @NotNull ... entryData) throws ColumnException;

        @NotNull Optional<? extends @NotNull Element> get(int index);

        boolean delete(int index);

        @Unmodifiable @NotNull Collection<? extends @NotNull Element> getAll();
    }

    interface Columns {

        @NotNull Table getTable();

        /**
         * @return A Column key created
         * @throws NoEmptyTableException if you have an element in the table, you can't create a column key
         * @throws ColumnAlreadyExistsException if column {@code name} already exists
         * */
        <E> @NotNull Column<E> create(@NotNull String name, @NotNull DataType<E> dataType) throws ColumnAlreadyExistsException, NoEmptyTableException;

        /**
         * @return A standard column created
         * @throws ColumnException if any column errors occurs
         * */
        <E> @NotNull Column<E> create(@NotNull String name, @NotNull DataType<E> dataType, @Nullable E defaultValue, boolean isNullable) throws ColumnException;

        @NotNull Optional<? extends @NotNull Column<?>> get(@NotNull String columnName);

        boolean delete(@NotNull String columnName);

        @Unmodifiable @NotNull Collection<? extends @NotNull Column<?>> getAll();

        default @Unmodifiable @NotNull Collection<? extends @NotNull Column<?>> getKeys() {
            return Collections.unmodifiableCollection(getAll().stream().filter(Column::isKey).collect(Collectors.toList()));
        }
    }
}
