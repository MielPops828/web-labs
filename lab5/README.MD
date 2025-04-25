В этой лабе для настройки совместимости фронтенда с бэкендом можно использовать HashLocation, вместо реализации webconfig.

Пример реализации:

const appRoutes: Routes = [];
bootstrapApplication(AppComponent,
  {
    providers: [
      provideRouter(appRoutes, withHashLocation())
    ]
  }
);