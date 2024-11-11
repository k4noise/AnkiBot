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