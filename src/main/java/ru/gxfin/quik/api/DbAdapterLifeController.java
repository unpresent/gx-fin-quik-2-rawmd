package ru.gxfin.quik.api;

import ru.gxfin.quik.events.DbAdapterStartEvent;
import ru.gxfin.quik.events.DbAdapterStopEvent;

public interface DbAdapterLifeController {
    /**
     * Обработчик команды о запуске провайдера
     * @param event команда о запуске провайдера
     */
    void onEvent(DbAdapterStartEvent event);

    /**
     * Обработчик команды об остановке провайдера
     * @param event команда об остановке провайдера
     */
    void onEvent(DbAdapterStopEvent event);
}
