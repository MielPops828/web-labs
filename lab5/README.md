Для этой работы был взят backend-проект lab2 и frontend-проект lab4.

**Изменения:**
- добавление родительского pom с указанием модулей (сначала lab4, заьем lab2, это важно);
- дочерний pom в lab4 с frontend-maven-plugin;
- дочерний pom в lab2 с maven-resources-plugin;
- в бэкенд был добавлен webconfig для обработки статических файлов из папки static и для перенаправления запросов на index.html клиентского роутинга;
- в бэкенд был добавлен corsconfig для решения проблем с взаимодействием модулей из разных доменов;
- в application.properties при возникновении ошибки маппинга (webconfig) указать: properties spring.mvc.pathmatch.matching-strategy=ant_path_matcher.

**Как это работает вместе?**
1. Пользователь открывает, например, `http://localhost:8080/projects/4`.  
2. Spring Boot перенаправляет запрос на `index.html` (благодаря `WebConfig`).  
3. Angular загружается и анализирует путь `/projects/4`.  
4. Angular отправляет запрос к API: `http://localhost:8080/api/projects/4`.  
5. CORS разрешает этот запрос (благодаря `CorsConfig`).  
6. Бэкенд возвращает данные, и Angular отображает страницу.

