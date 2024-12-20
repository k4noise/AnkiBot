## Задача 1: Базовая структура проекта
Приветственный экран - у вас есть <такие> колоды


<div>Термин - уникальная сущность (id) внутри одной колоды, создать вторую такую же нельзя</div>
<div>Все операции с карточками доступны только внутри колоды </div>
<div>Справка (перечень доступных команд): `/help`</div>
<div>Просмотр всех колод: `/list_decks`</div>

__Колода__:
- Создание колоды: `/create_deck <название колоды>`
- Переименование колоды: `/rename_deck <название колоды>:= <новое название>`
- Удаление колоды: `/delete_deck <название колоды>`, также приводит к удалению всех карт внутри колоды
- Просмотр карточек колоды: `/list_cards <название колоды> (термин = определение)`
 
__Карточка__:
- Создание карточки: `/create_card <название колоды>:<термин>=<определение>`
- Редактирование термина: `/edit_card_term <название колоды>:<термин>:=<новое название>`
- Редактирование определения: `/edit_card_def <название колоды>:<термин> =<новое определение>`
- Удаление карточки: `/delete_card <название колоды>:<термин>`
- Просмотр карточки: `/list_card <название колоды>:<термин>`

__Пример__:

<div>P: У Вас пока нет ни одной колоды, создайте первую /create_deck <название></div>
<div>U: /create_deck Git</div>
<div>P: Колода Git успешно добавлена</div>
<div>U: /create_card Git:branch = ветка</div>
<div>P: Карта с термином “branch” была успешно добавлена в колоду Git</div>
<div>U: /create_card Git : commit = коммит</div>
<div>P: Карта с термином “commit” была успешно добавлена в колоду Git</div>
<div>U: /list_cards Git</div>
<div>P: Git:</div>
<div>P: branch = ветка</div>
<div>P: commit = коммит</div>
<div>U:  /list_decks</div>
<div>P: “Git”: 2 карточки </div>
<div>U: /edit_card_term Git:commit := commit2</div>
<div>P: Изменен термин: “commit” -> “commit2”</div>
<div>U: /edit_card_def Git : commit2 = фиксация изменений</div>
<div>P: Определение карты было успешно изменено: "commit2" = фиксация изменений</div>
<div>U: /delete_card Git:commit2</div>
<div>P: Карта с термином "commit2" была успешно удалена из колоды Git</div>
<div>U: /list_decks</div>
<div>P: “Git”: 1 карточка</div>
<div>U: /delete_deck Git </div>
<div>P: Колода Git была успешно удалена</div>
<div>U: /list_decks</div>
<div>P: У Вас пока нет ни одной колоды, создайте первую /create_deck <название></div>

## Задача 2: Реализация режимов обучения
Обучение
Команда: `/check <режим обучения>`

__Режимы обучения:__

- С вводом терминов: `/check typing: <название колоды>`
Пользователю показывается определение и ожидается ввод термина. 

- С оценкой запоминания карточек: `/check memory: <название колоды>`
Пользователю показывается термин и предлагается выбор из трех вариантов: не помню, частично помню и помню

- С сопоставлением термина определению: `/check match: <название колоды>`
Пользователю показывается определение и термин случайным образом и предлагается указать, подходит ли термин определению. Если, по мнению пользователя, термин соответствует определению, то пользователь вводит 1, иначе - 0. 

<div>Обучение автоматически завершается, когда пользователю были показаны все карточки в колоде.<div>
<div>Возможен досрочный выход из режима обучения: `/end-check`.<div>

<div>Если колода пустая, то начать обучение невозможно и программа уведомляет пользователя об отсутствии карточек для изучения.<div>

__Пример обучения по пустой колоде:__
<div>U: /create_deck GitPro<div>
<div>U: /check_typing GitPro<div>
<div>P: Колода не содержит карточек, доступных для изучения.<div>

__Пример обучения “ввод”:__
<div>U: /check typing: Git<div>
<div>P: Вы начали обучение с вводом термина.<div>
<div>P: Вам показывается определение, ваша задача - ввести термин. <div>
<div>P: Чтобы досрочно завершить сеанс воспользуйтесь командой /end_check<div>

<div>P: Определение - “ветка”. <div>
<div>P: Введите соответствующий термин: <div>
<div>U: branch<div>
<div>P: Верно! Правильный ответ: <div>
<div>P: “branch” - ветка<div>

<div>P: Определение - “фиксация изменений”. <div>
<div>P: Введите соответствующий термин: <div>
<div>U: comit<div>
<div>P: Неверно. Правильный ответ: <div>
<div>P: “commit” - фиксация изменений <div>

P: Вы прошли все карточки в колоде Git!

__Пример обучения “карточки”:__
<div>U: /check memory: Git<div>
<div>P: Вы начали обучение с карточками<div>
<div>P: Вам показывается термин, ваша задача - вспомнить определение<div>
<div>После оценить, насколько хорошо вы помните определение<div>
<div>P: 0 - не помню  1 - частично помню  2 - помню<div>
<div>P: Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check<div>

<div>P: Термин -  “commit” <div>
<div>U: 1<div>
<div>P: Верно!  Правильный ответ:<div>
<div>P: “commit” - фиксация изменений<div>

<div>P: Термин - “branch”<div>
<div>U:  2<div>
<div>P: Верно! Правильный ответ:<div>
<div>P:  “branch” - ветка<div>

<div>P: Вы прошли все карточки в колоде Git! <div>

__Пример обучения “соответствие”:__
<div>U: /check match: Git<div>
<div>P: Вы начали обучения в режиме соответствия<div>
<div>P: Показывается термин и определение, ваша задача - определить, соответствует ли термин определению<div>
<div>P: 0 - не соответствует, 1 - соответствует;<div>
<div>P: Чтобы досрочно завершить сеанс воспользуйтесь командой /end_check<div>

<div>P: Утверждение:<div>
<div>P: commit - ветка<div>
<div>P: 0 - нет; 1 - да<div>
<div>U: 1 <div>
<div>P: Неверно. Правильный ответ: <div>
<div>P: commit - фиксация изменений<div>

<div>P: Утверждение:<div>
<div>P: branch - ветка<div>
<div>P: 0 - нет; 1 - да<div>
<div>U: 1<div>
<div>P: Верно! Правильный ответ:  <div>
<div>P: branch - ветка<div>

<div>P: Вы прошли все карточки в колоде Git!<div>
