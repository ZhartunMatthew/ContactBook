Скрипт к созданию БД лежит в contact-common\src\main\resources<br>
Прикрепленные файлы хранятся на диске<br>
Путь к файлам указан в файле contact-common\src\main\resources\directories.properties, указанная папка должна быть создана до начала работы<br>
Имя пользователя и пароль к БД необходимо указать в файле contact-web\src\main\webapp\META-INF\context.xml<br>
Если изменяете имя схемы БД, то не забудьте сменить его в классе ConnectionManager при колучении DataSource