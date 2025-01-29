package com.example.logowanie;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import com.example.logowanie.Main;

import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class ToolBar {
    private final HBox toolBar;
    private final LanguageManager languageManager;
    private final Theme themeManager;
    private final Bottom bottomSection;
    private final Main main;
    private int userId;
    private int permissions;
    private MenuAplikacji menuAplikacji;

    public ToolBar(LanguageManager languageManager, Theme themeManager, Bottom bottomSection, Main main, int userId, MenuAplikacji menuAplikacji) {
        this.languageManager = languageManager;
        this.themeManager = themeManager;
        this.bottomSection = bottomSection;
        this.toolBar = createToolBar();
        this.main = main;
        this.userId = userId;
        this.menuAplikacji = menuAplikacji;

        this.permissions = MSController.getUserPermissions(userId);

    }

    private HBox createToolBar() {
        HBox toolBar = new HBox(10);
        toolBar.setStyle("-fx-padding: 10; -fx-background-color: black;");

        Text NaszaMarka = new Text("JBTSound");
        NaszaMarka.setFill(Color.web("#1DB954"));
        NaszaMarka.setTextAlignment(TextAlignment.CENTER);
        NaszaMarka.setTextOrigin(VPos.CENTER);
        NaszaMarka.setFont(new Font("Arial", 18));

        Label logoLabel = createLogoLabel();

        Button utworyButton = createToolBarButton("menu.utwory", "🎵");
        Button changeThemeButton = createToolBarButton("menu.theme", "🌙");
        Button changeLanguageButton = createToolBarButton("menu.language", "🌍");
        Button regulationsButton = createToolBarButton("menu.regulations", "📜");

        Button profileButton = new Button("👤");
        profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-font-size: 16;");
        profileButton.setOnAction(e -> main.openProfileWindow(userId));

        Button logoutButton = new Button("🚪");
        logoutButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-font-size: 16;");
        logoutButton.setOnAction(e -> main.openLoginWindow());

        changeThemeButton.setOnAction(e -> {
            themeManager.toggleTheme();
            themeManager.applyTheme(toolBar.getScene());
        });


        changeLanguageButton.setOnAction(actionEvent -> {
            boolean isEnglish = languageManager.isEnglish();
            languageManager.setLanguage(!isEnglish);

            utworyButton.setText("🎵 " + languageManager.getText("menu.utwory"));
            changeThemeButton.setText("🌙 " + languageManager.getText("menu.theme"));
            changeLanguageButton.setText("🌍 " + languageManager.getText("menu.language"));
            regulationsButton.setText("📜 " + languageManager.getText("menu.regulations"));

            bottomSection.updateBiggestHitsText();

        });

        regulationsButton.setOnAction(actionEvent -> showRegulations());
        utworyButton.setOnAction(actionEvent -> showSongMenu(utworyButton));

        HBox leftSide = new HBox(10, logoLabel, NaszaMarka, utworyButton, changeThemeButton, changeLanguageButton, regulationsButton);
        leftSide.setSpacing(10);
        leftSide.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox rightSide = new HBox(10, profileButton, logoutButton);
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        toolBar.getChildren().addAll(leftSide, spacer, rightSide);

        return toolBar;
    }


    private Label createLogoLabel() {
        Label label = new Label();
        try {
            Image logoImage = new Image(getClass().getResource("/logo.png").toExternalForm());
            ImageView logoImageView = new ImageView(logoImage);
            logoImageView.setPreserveRatio(true);
            label.setGraphic(logoImageView);

            Circle clip = new Circle(25, 25, 25);
            logoImageView.setClip(clip);

            logoImageView.setFitWidth(50);
            logoImageView.setFitHeight(50);

            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(logoImageView);
            rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
            rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
            rotateTransition.setFromAngle(0);
            rotateTransition.setToAngle(360);
            rotateTransition.setRate(0.3);
            rotateTransition.setDuration(Duration.seconds(5));

            rotateTransition.play();

            Glow glowEffect = new Glow(0.0);
            logoImageView.setEffect(glowEffect);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(glowEffect.levelProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(glowEffect.levelProperty(), 0.8)),
                    new KeyFrame(Duration.seconds(2), new KeyValue(glowEffect.levelProperty(), 0.0))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);
            timeline.play();

        } catch (NullPointerException e) {
            label.setText("Logo");
            label.setStyle("-fx-font-size: 16; -fx-text-fill: #3C3C3C;");
        }
        return label;
    }
    private void showSongMenu(Button utworyButton) {
        ContextMenu songMenu = new ContextMenu();


        if (permissions == 1){
            MenuItem addSongItem = new MenuItem(languageManager.getText("menu.add.song"));
            addSongItem.setOnAction(e -> addSong());
            songMenu.getItems().add(addSongItem);

            MenuItem removeSongItem = new MenuItem(languageManager.getText("menu.remove.song"));
            removeSongItem.setOnAction(e -> removeSongFromList());
            songMenu.getItems().add(removeSongItem);
        }

        MenuItem songListItem = new MenuItem(languageManager.getText("menu.song.list"));
        songListItem.setOnAction(e -> menuAplikacji.showSongList());
        songMenu.getItems().add(songListItem);

        songMenu.show(utworyButton, Side.BOTTOM, 0, 0);
    }


    private void removeSongFromList() {
        TableView<Song> tableView = new TableView<>();

        TableColumn<Song, String> titleColumn = new TableColumn<>(languageManager.getText("add.title"));
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        titleColumn.setPrefWidth(200);

        TableColumn<Song, String> firstNameColumn = new TableColumn<>(languageManager.getText("add.fname"));
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameColumn.setPrefWidth(150);

        TableColumn<Song, String> lastNameColumn = new TableColumn<>(languageManager.getText("add.lname"));
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameColumn.setPrefWidth(150);

        TableColumn<Song, String> pseudoColumn = new TableColumn<>(languageManager.getText("add.pseudo"));
        pseudoColumn.setCellValueFactory(cellData -> cellData.getValue().pseudoProperty());
        pseudoColumn.setPrefWidth(150);

        TableColumn<Song, String> dateColumn = new TableColumn<>(languageManager.getText("add.data"));
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateAddedProperty());
        dateColumn.setPrefWidth(150);

        tableView.getColumns().addAll(titleColumn, firstNameColumn, lastNameColumn, pseudoColumn, dateColumn);

        List<Song> songList = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT utwory.nazwa_utworu, tworcy.pseudonim, utwory.data_dodania, tworcy.imie_tworcy, tworcy.nazwisko_tworcy, utwory.id " +
                    "FROM utwory JOIN tworcy ON utwory.tworca_id = tworcy.id;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String songTitle = resultSet.getString("nazwa_utworu");
                String imieTworcy = resultSet.getString("imie_tworcy");
                String nazwiskoTworcy = resultSet.getString("nazwisko_tworcy");
                String author = resultSet.getString("pseudonim");
                String dataDodania = resultSet.getString("data_dodania");
                int songId = resultSet.getInt("id");
                songList.add(new Song(songTitle, imieTworcy, nazwiskoTworcy, author, dataDodania, songId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            songList.add(new Song("Błąd połączenia z bazą danych", "", "", "", "", -1));
        }

        final int rowsPerPage = 8;
        int totalRows = songList.size();
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        final int[] currentPage = {1};

        Runnable updateTable = () -> {
            tableView.getItems().clear();
            int startIndex = (currentPage[0] - 1) * rowsPerPage;
            int endIndex = Math.min(startIndex + rowsPerPage, totalRows);
            for (int i = startIndex; i < endIndex; i++) {
                tableView.getItems().add(songList.get(i));
            }
        };

        Button prevButton = new Button(languageManager.getText("show.prev"));
        Button nextButton = new Button(languageManager.getText("show.next"));

        prevButton.setOnAction(e -> {
            if (currentPage[0] > 1) {
                currentPage[0]--;
                updateTable.run();
            }
        });

        nextButton.setOnAction(e -> {
            if (currentPage[0] < totalPages) {
                currentPage[0]++;
                updateTable.run();
            }
        });

        Button removeButton = new Button(languageManager.getText("menu.remove.song"));
        removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");

        if (permissions==1) {
            removeButton.setOnAction(e -> {
                Song selectedSong = tableView.getSelectionModel().getSelectedItem();
                if (selectedSong != null) {
                    try (Connection connection = MySQLConnection.getConnection()) {
                        String query = "DELETE FROM utwory WHERE id = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, selectedSong.getSongId());
                        int rowsAffected = statement.executeUpdate();

                        if (rowsAffected > 0) {
                            tableView.getItems().remove(selectedSong);
                            showAlert("Sukces", "", "Utwór został usunięty pomyślnie.");
                        } else {
                            showAlert("Błąd", "", "Nie udało się usunąć utworu.");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showAlert("Błąd połączenia", "", "Wystąpił problem z połączeniem do bazy danych.");
                    }
                } else {
                    showAlert("Brak wyboru", "", "Wybierz utwór do usunięcia.");
                }
            });
        } else { removeButton.setDisable(true);}

        HBox buttonPanel = new HBox(10, prevButton, nextButton, removeButton);
        buttonPanel.setStyle("-fx-alignment: center; -fx-padding: 10px;");

        Label headerLabel = new Label(languageManager.getText("menu.remove.song"));
        headerLabel.setFont(new Font("SansSerif", 18));
        headerLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        VBox vBox = new VBox(10, headerLabel, tableView, buttonPanel);
        vBox.setStyle("-fx-padding: 20px; -fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-width: 1px;");

        Scene scene = new Scene(vBox, 800, 500);

        Stage dialogStage = new Stage();
        dialogStage.setTitle(languageManager.getText("menu.remove.song"));
        dialogStage.setScene(scene);
        dialogStage.show();

        Platform.runLater(updateTable);
    }


    public void addSong() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(languageManager.getText("menu.add.song"));

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #f4f4f9; -fx-border-color: #d1d1d1; -fx-border-width: 1px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label headerLabel = new Label(languageManager.getText("menu.add.song"));
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #444; -fx-padding: 10px;");
        headerLabel.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(languageManager.getText("add.title"));
        TextField titleField = new TextField();
        Label firstNameLabel = new Label(languageManager.getText("add.fname"));
        TextField firstNameField = new TextField();
        Label lastNameLabel = new Label(languageManager.getText("add.lname"));
        TextField lastNameField = new TextField();
        Label pseudonymLabel = new Label(languageManager.getText("add.pseudo"));
        TextField pseudonymField = new TextField();
        Label dateLabel = new Label(languageManager.getText("add.data"));
        TextField dateField = new TextField(LocalDate.now().toString());

        String labelStyle = "-fx-font-size: 14px; -fx-text-fill: #333;";
        String fieldStyle = "-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;";

        titleLabel.setStyle(labelStyle);
        firstNameLabel.setStyle(labelStyle);
        lastNameLabel.setStyle(labelStyle);
        pseudonymLabel.setStyle(labelStyle);
        dateLabel.setStyle(labelStyle);

        titleField.setStyle(fieldStyle);
        firstNameField.setStyle(fieldStyle);
        lastNameField.setStyle(fieldStyle);
        pseudonymField.setStyle(fieldStyle);
        dateField.setStyle(fieldStyle);

        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(firstNameLabel, 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(lastNameLabel, 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(pseudonymLabel, 0, 3);
        grid.add(pseudonymField, 1, 3);
        grid.add(dateLabel, 0, 4);
        grid.add(dateField, 1, 4);

        Button addButton = new Button(languageManager.getText("add.add"));
        Button cancelButton = new Button(languageManager.getText("add.cancel"));

        String buttonStyle = "-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8 15; -fx-font-weight: bold;";
        String buttonHoverStyle = "-fx-background-color: #0056b3;";

        addButton.setStyle(buttonStyle);
        cancelButton.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #444; -fx-font-size: 14px; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8 15; -fx-font-weight: bold;");

        if (permissions != 1) {addButton.setDisable(true);}

        addButton.setOnMouseEntered(e -> addButton.setStyle(buttonHoverStyle));
        addButton.setOnMouseExited(e -> addButton.setStyle(buttonStyle));
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #d6d6d6;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #444;"));

        addButton.setOnAction(e -> {
            String title = titleField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String pseudonym = pseudonymField.getText().trim();
            String date = dateField.getText().trim();

            if (title.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || pseudonym.isEmpty() || date.isEmpty()) {
                showErrorDialog(languageManager.getText("error.empty.fields"));
                return;
            }

            try (Connection connection = MySQLConnection.getConnection()) {
                String checkAuthorQuery = "SELECT id FROM tworcy WHERE imie_tworcy = ? AND nazwisko_tworcy = ? AND pseudonim = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkAuthorQuery);
                checkStatement.setString(1, firstName);
                checkStatement.setString(2, lastName);
                checkStatement.setString(3, pseudonym);
                ResultSet resultSet = checkStatement.executeQuery();

                int authorId;
                if (resultSet.next()) {
                    authorId = resultSet.getInt("id");
                } else {
                    String insertAuthorQuery = "INSERT INTO tworcy (imie_tworcy, nazwisko_tworcy, pseudonim) VALUES (?, ?, ?)";
                    PreparedStatement insertAuthorStatement = connection.prepareStatement(insertAuthorQuery, Statement.RETURN_GENERATED_KEYS);
                    insertAuthorStatement.setString(1, firstName);
                    insertAuthorStatement.setString(2, lastName);
                    insertAuthorStatement.setString(3, pseudonym);
                    insertAuthorStatement.executeUpdate();

                    ResultSet generatedKeys = insertAuthorStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        authorId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Nie udało się uzyskać ID nowo dodanego twórcy.");
                    }
                }

                String insertSongQuery = "INSERT INTO utwory (nazwa_utworu, tworca_id, data_dodania) VALUES (?, ?, ?)";
                PreparedStatement insertSongStatement = connection.prepareStatement(insertSongQuery);
                insertSongStatement.setString(1, title);
                insertSongStatement.setInt(2, authorId);
                insertSongStatement.setString(3, date);
                insertSongStatement.executeUpdate();
                showSuccessDialog(languageManager.getText("menu.song.added"));
                dialogStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                showErrorDialog(languageManager.getText("error.connection"));
            }
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        HBox buttonLayout = new HBox(15, addButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setPadding(new Insets(20));

        mainLayout.getChildren().addAll(headerLabel, grid, buttonLayout);

        Scene scene = new Scene(mainLayout, 500, 450);
        dialogStage.setScene(scene);
        dialogStage.show();
    }


    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Song {
        private final StringProperty title = new SimpleStringProperty();
        private final StringProperty firstName = new SimpleStringProperty();
        private final StringProperty lastName = new SimpleStringProperty();
        private final StringProperty pseudo = new SimpleStringProperty();
        private final StringProperty dateAdded = new SimpleStringProperty();
        private final IntegerProperty songId = new SimpleIntegerProperty();

        public Song(String title, String firstName, String lastName, String pseudo, String dateAdded) {
            this(title, firstName, lastName, pseudo, dateAdded, -1);
        }

        public Song(String title, String firstName, String lastName, String pseudo, String dateAdded, int songId) {
            this.title.set(title);
            this.firstName.set(firstName);
            this.lastName.set(lastName);
            this.pseudo.set(pseudo);
            this.dateAdded.set(dateAdded);
            this.songId.set(songId);
        }

        public String getTitle(){
            return title.get();
        }

        public StringProperty titleProperty() {
            return title;
        }

        public StringProperty firstNameProperty() {
            return firstName;
        }

        public StringProperty lastNameProperty() {
            return lastName;
        }

        public StringProperty pseudoProperty() {
            return pseudo;
        }

        public StringProperty dateAddedProperty() {
            return dateAdded;
        }

        public IntegerProperty songIdProperty() {
            return songId;
        }

        public int getSongId() {
            return songId.get();
        }

        public void setSongId(int songId) {
            this.songId.set(songId);
        }
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    private Button createToolBarButton(String textKey, String icon) {
        String text = languageManager.getText(textKey);
        Button button = new Button(icon + " " + text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #3C3C3C; -fx-font-size: 16;");
        return button;
    }

    public HBox getToolBar() {
        return toolBar;
    }

    private void showRegulations() {
        String fileName = languageManager.isEnglish() ? "regulamin_en.txt" : "regulamin.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder regulationsContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                regulationsContent.append(line).append("\n");
            }

            Stage regulationsStage = new Stage();
            regulationsStage.setTitle(languageManager.getText("menu.regulations"));

            TextArea textArea = new TextArea(regulationsContent.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setStyle("-fx-font-size: 14; -fx-font-family: 'Segoe UI';");

            if (themeManager.isDark()) {
                textArea.setStyle("-fx-control-inner-background: #323232; -fx-text-fill: white; -fx-font-size: 14;");
            } else {
                textArea.setStyle("-fx-control-inner-background: white; -fx-text-fill: black; -fx-font-size: 14;");
            }

            Button closeButton = new Button(languageManager.getText("menu.cancel"));
            closeButton.setStyle("-fx-font-size: 14;");
            closeButton.setOnAction(e -> regulationsStage.close());

            VBox layout = new VBox(10, textArea, closeButton);
            layout.setPadding(new Insets(10));
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 600, 400);
            themeManager.applyTheme(scene);
            regulationsStage.setScene(scene);

            regulationsStage.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getText("menu.regulations.error"));
            alert.showAndWait();
        }
    }

}