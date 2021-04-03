### Установка и запуск SUT

 * Запустить контейнер с mysql командой docker-compose up
 * Запустить приложение командой:
   java -jar artifacts/app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass
 * Запустить автотесты
 * Для перезапуска SUT следует остановить jar-приложение с помощью Ctrl+C, а затем снова запустить.
