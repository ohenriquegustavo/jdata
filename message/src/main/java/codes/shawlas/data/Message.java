package codes.shawlas.data;

import codes.shawlas.data.exception.MessageExecutionException;
import org.jetbrains.annotations.NotNull;

public sealed interface Message permits Message.Input, Message.Output {

    @NotNull String getId();

    // Classes

    non-sealed interface Input extends Message {

        /**
         * @throws MessageExecutionException if an error occurs while execute the response ({@linkplain Input##optional-restrictions optional})
         * */
        void execute(@NotNull Object @NotNull ... args);

    }

    non-sealed interface Output extends Message {
    }
}