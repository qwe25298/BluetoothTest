# BluetoothTest

Реализует поиск новых и вывод уже сопряженных устойств при помощи bluetooth.

**SDK: 33. Android 13**

**Состоит из трех activity-страниц:**
+ MainActivity.java (стартовая)
+ boundedList.java (поиск сопряженных устройств)
+ findDev.java (поиск новых устройств)

**Список требуемых разрешений:**

+ BLUETOOTH_ADMIN
+ BLUETOOTH_SCAN
+ EXTRA_DISCOVERABLE_DURATION (300 сек.)
+ ACCESS_COARSE_LOCATION


# MainActivity.java

- Выполняет только функцию стартовой страницы
- Реализована кнопка: `private Button button;` при нажатии перевоящая на следующуя страницу **boundedList.java**
- Переход на страницу реализуется `android:onClick="goList"` в **xml** файле к **MainActivity**, тем самым при нажатии вызывая функцию `public void goList(View view) {
        Intent intent = new Intent(this, boundedList.class);
        startActivity(intent); }` в которой реализуется переход на другую страницу
- В дальнейшем, переходы на другие **activity** реализованы таким же способом

- - - 

# boundedList.java

- Реализует вывод сопряженных по bluetooth устройств
- Объявляем и инициализируем переменную **adapter** `BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();`, подключаясь тем самым к **bluetooth** модулю устройства
- Инициализируем множество **Set** с именем **pairedDevices**, в котором будут хранится элементы полученных устройств типа BluetoothDevice и получаем данные при помощи ` Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();`
- Далее в `ArrayList<String> list = null;`, объявленную ранее, записываем имена устройств с помощью `.getName()` и аппаратный адрес локального адаптера Bluetooth `.getAddress()`
- Инициализаруем переменную `ArrayAdapter<String> ad`, для наиболее удобного взаимодействия с визуальным отображением списка, используем конструктор `ArrayAdapter<String> ad = new ArrayAdapter<>(boundedList.this, android.R.layout.simple_list_item_1, list);`, вида `public ArrayAdapter (Context context, int resource, List<T> objects)`
- Добавляем элементы из списка в ListView `ListView myList = findViewById(R.id.List);`, при помощи `myList.setAdapter(ad);`
- Реализована кнопка, переводящая на следующую **activity** ` public void goList_2(View view) {
        Intent intent = new Intent(this, findDev.class);
        startActivity(intent); }`, нажатие на которую вызывает функцию **goList_2**

# findDev.java

- Реализует поиск доступных bluetooth утройств поблизости
- **Запрашиваемые разрешения**
  - `ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                    DANGEROUS_RESULT_CODE);`
  - `discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivityForResult(discoverableIntent, requestCode);`
  - `ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                DANGEROUS_RESULT_CODE);`
- Инициализация списка в который будут представлены устройства `listDevices = findViewById(R.id.myList);`
- Инициализация кнопки начала поиска устройств `scanButton = findViewById(R.id.butt);`
- Отслеживание нажатия кнопки поиска `scanButton.setOnClickListener(new View.OnClickListener()...`
- ` IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(myReciever, intentFilter);` создание **intent** фильтра для отслеживания события нахождения новго **bluetooth** утройства.
- **MyReciever** 'это BroadcastReciever для выполнения **intent** в **registerReceiver** `roadcastReceiver myReciever = new BroadcastReceiver() {...`

