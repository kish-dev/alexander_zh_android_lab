# Custom View

#### Обязательные задачи:


- [ ] Добавить кнопку для перехода на экран корзины
- [ ] Добавить экран корзины. Переиспользовать список продуктов, чтобы заново не делать адаптер
- [ ] Custom View
- [ ] Сохранить состояние custom view через onSaveInstanceState/onRestoreInstanceState методах и завести модельку наследника от SavedState

Кнопка, которая будет создана из layout:

Сама кнопка представляет собой кнопку под item в списке продуктов и в детальном рассмотрении продукта, а также в корзине.
У продукта в базе данных будет inCartCount, если 0 - продукт не находится в корзине, если 1 и более - продукт в корзине. При каждом изменении количества продукта в корзине мы изменяем кол-во продуктов в корзине для нашей БД.

1. Кнопка с иконкой корзины и текстом "В корзину", белый текст на синем фоне. При клике на кнопку переходим в состояние 2. Справа снизу кнопки "В корзину" рисуется светло-зеленый круг, с белой галочкой внутри. Галочка и круг тоже должны быть нарисованы на canvas. Зеленый круг наполовину справа и сверху наезжает на синюю кнопку. При переходе к состоянию 2 паддинг снизу сохраняется для бесшовности.
2. Кнопка, на полупрозрачном и более темном синем фоне, где слева будет рисоваться кликабельный "-", справа кликабельный "+". Изначально будет инициализироваться "1". При клике на "+" будет увеличиваться количество товаров в корзине, при клике на "-" будет уменьшаться количество товаров в корзине. Если кликаем на "-", когда у нас 1 товар, кнопка будет возвращаться в состояние 1. Сами "+" и "-" должны быть нарисованы на canvas с помощью paint, должны быть представлены как прямоугольники с скругленными краями. При долгом нажатии на "+" или "-" у количество товаров будет набираться поочередно, но достаточно быстро - по 5-10 в секунду. Главное, чтобы они добавлялись через одинаковое время для сохранения приятного глазу изменения counter'а 

Где посмотреть исходники как должно работать - приложение Ozon. Что мы меняем - круг справа внизу и надпись
