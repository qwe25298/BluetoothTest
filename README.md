# BluetoothTest

Реализует поиск новых и вывод уже сопряженных устойств при помощи bluetooth.

**

**Список требуемых разрешений:**

+ BLUETOOTH_ADMIN
+ BLUETOOTH_SCAN
+ EXTRA_DISCOVERABLE_DURATION (300 сек.)
+ ACCESS_COARSE_LOCATION


# MainActivity.java

- Выполняет только функцию стартовой страницы
- Реализована кнопка: `private Button button;` при нажатии перевоящая на следующуя страницу **boundedList.java**

- - - 

# boundedList.java

- Реализует вывод сопряженных по bluetooth устройств
- Объявляем и инициализируем переменную **adapter** `BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();`, подключаясь тем самым к **bluetooth** модулю устройства
- Инициализируем множество **Set** с именем **pairedDevices**, в котором будут хранится элементы полученных устройств типа BluetoothDevice и получаем данные при помощи ` Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();`
- Далее в `ArrayList<String> list = null;`, объявленную ранее, записываем имена устройств с помощью `.getName()` и аппаратный адрес локального адаптера Bluetooth `.getAddress()`
- Инициализаруем переменную `ArrayAdapter<String> ad`, для наиболее удобного взаимодействия с визуальным отображением списка, используем конструктор `ArrayAdapter<String> ad = new ArrayAdapter<>(boundedList.this, android.R.layout.simple_list_item_1, list);`, вида `public ArrayAdapter (Context context, int resource, List<T> objects)`
- Добавляем элементы из списка в ListzView `ListView myList = findViewById(R.id.List);`, при помощи `myList.setAdapter(ad);`



