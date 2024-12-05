package com.example.nosql;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImagePath = ""; // Хранение пути к выбранному изображению

    private ListView listView;
    private EditText nameEditText, sizeEditText, descriptionEditText;
    private ArrayList<Clothing> clothingList2;
    private ClothingAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this); // Инициализация Paper

        listView = findViewById(R.id.listView);
        nameEditText = findViewById(R.id.nameEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        // Загрузка списка одежды из Paper
        loadClothing();

        // Настройка адаптера для ListView
        adapter2 = new ClothingAdapter(this, clothingList2);
        listView.setAdapter(adapter2);

        // Обработчик кликов для выбора элемента
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Clothing selectedClothing = clothingList2.get(position);
                populateFields(selectedClothing);
            }
        });
    }

    private void loadClothing() {
        // Загрузка списка одежды из Paper или создание нового списка
        clothingList2 = Paper.book().read("clothing", new ArrayList<Clothing>());
    }

    private void populateFields(Clothing clothing) {
        nameEditText.setText(clothing.getName());
        sizeEditText.setText(clothing.getSize());
        descriptionEditText.setText(clothing.getDescription());
    }

    public void addClothing(View view) {
        String name = nameEditText.getText().toString();
        String size = sizeEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (!name.isEmpty() && !size.isEmpty() && !description.isEmpty() && !selectedImagePath.isEmpty()) {
            Clothing clothing = new Clothing(name, size, description, selectedImagePath);
            clothingList2.add(clothing);

            // Сохранение изменений в Paper
            Paper.book().write("clothing", clothingList2);
            adapter2.notifyDataSetChanged();
            Toast.makeText(this, "Одежда добавлена!", Toast.LENGTH_SHORT).show();

            // Очистка полей
            selectedImagePath = "";
        } else {
            Toast.makeText(this, "Введите все параметры и выберите фото!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeClothing(View view) {
        int position = listView.getCheckedItemPosition();
        if (position != ListView.INVALID_POSITION) {
            clothingList2.remove(position);
            Paper.book().write("clothing", clothingList2);
            adapter2.notifyDataSetChanged();
            nameEditText.setText(""); // Очистка полей
            sizeEditText.setText("");
            descriptionEditText.setText("");
            Toast.makeText(this, "Одежда удалена!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Выберите одежду для удаления", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateClothing(View view) {
        int position = listView.getCheckedItemPosition();
        String name = nameEditText.getText().toString();
        String size = sizeEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (position != ListView.INVALID_POSITION && !name.isEmpty() && !size.isEmpty() && !description.isEmpty()) {
            Clothing updatedClothing = new Clothing(name, size, description, selectedImagePath);
            clothingList2.set(position, updatedClothing);
            Paper.book().write("clothing", clothingList2);
            adapter2.notifyDataSetChanged();
            Toast.makeText(this, "Одежда обновлена!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Выберите одежду и введите новые данные!", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Путь к изображению
            selectedImagePath = imageUri.toString();

            // Выводим сообщение, чтобы удостовериться в успешной загрузке
            Toast.makeText(this, "Фото выбрано!", Toast.LENGTH_SHORT).show();
        } else {
            // Если результат не был успешным2
            Toast.makeText(this, "Ошибка выбора изображения", Toast.LENGTH_SHORT).show();
        }
    }

}
