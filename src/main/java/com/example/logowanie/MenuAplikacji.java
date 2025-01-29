package com.example.logowanie;

import com.example.logowanie.ToolBar.Song;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.SVGPath;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuAplikacji {
    private Stage primaryStage;
    private final LanguageManager languageManager;
    private final Theme themeManager;
    private final Main main;
    private int userId;
    private BorderPane root;
    private Profile profile;

    public MenuAplikacji(Stage primaryStage, LanguageManager languageManager, Theme themeManager, Main main, int userId) {
        this.primaryStage = primaryStage;
        this.languageManager = languageManager;
        this.themeManager = themeManager;
        this.main = main;
        this.userId = userId;


        try {
            initializeUI(main);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUI(Main main) {
        root = new BorderPane();

        Bottom bottomSection = new Bottom(languageManager);

        ToolBar toolBar = new ToolBar(languageManager, themeManager, bottomSection, main, userId, this);
        root.setTop(toolBar.getToolBar());


        root.setBottom(bottomSection.getBottomSection());

        Scene scene = new Scene(root, 900, 600);
        themeManager.applyTheme(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showSongList() {
        TableView<ToolBar.Song> tableView = new TableView<>();

        TableColumn<ToolBar.Song, String> titleColumn = new TableColumn<>(languageManager.getText("add.title"));
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        titleColumn.setPrefWidth(200);

        TableColumn<ToolBar.Song, String> firstNameColumn = new TableColumn<>(languageManager.getText("add.fname"));
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameColumn.setPrefWidth(150);

        TableColumn<ToolBar.Song, String> lastNameColumn = new TableColumn<>(languageManager.getText("add.lname"));
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameColumn.setPrefWidth(150);

        TableColumn<ToolBar.Song, String> pseudoColumn = new TableColumn<>(languageManager.getText("add.pseudo"));
        pseudoColumn.setCellValueFactory(cellData -> cellData.getValue().pseudoProperty());
        pseudoColumn.setPrefWidth(150);

        TableColumn<ToolBar.Song, String> dateColumn = new TableColumn<>(languageManager.getText("add.data"));
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateAddedProperty());
        dateColumn.setPrefWidth(150);

        TableColumn<ToolBar.Song, Void> buyColumn = new TableColumn<>("Kup");
        buyColumn.setCellFactory(col -> new TableCell<ToolBar.Song, Void>() {
            private final Button buyButton = createBuyButton();

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buyButton);
                }
            }
        });

        tableView.getColumns().addAll(titleColumn, firstNameColumn, lastNameColumn, pseudoColumn, dateColumn, buyColumn);


        List<Object[]> songData = new ArrayList<>();
        try (Connection connection = MySQLConnection.getConnection()) {
            String query = "SELECT utwory.nazwa_utworu, tworcy.pseudonim, utwory.data_dodania, tworcy.imie_tworcy, tworcy.nazwisko_tworcy " +
                    "FROM utwory JOIN tworcy ON utwory.tworca_id = tworcy.id;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String songTitle = resultSet.getString("nazwa_utworu");
                String imieTworcy = resultSet.getString("imie_tworcy");
                String nazwiskoTworcy = resultSet.getString("nazwisko_tworcy");
                String author = resultSet.getString("pseudonim");
                String dataDodania = resultSet.getString("data_dodania");
                songData.add(new Object[]{songTitle, imieTworcy, nazwiskoTworcy, author, dataDodania});
            }

            if (songData.isEmpty()) {
                songData.add(new Object[]{"Brak utworów w bazie danych", "", "", "", ""});
            }
        } catch (Exception e) {
            e.printStackTrace();
            songData.add(new Object[]{"Błąd połączenia z bazą danych", "", "", "", ""});
        }

        Platform.runLater(() -> {
            tableView.getItems().clear();
            for (Object[] data : songData) {
                tableView.getItems().add(new ToolBar.Song(
                        (String) data[0],
                        (String) data[1],
                        (String) data[2],
                        (String) data[3],
                        (String) data[4]
                ));

                //Button buyButton = createBuyButton(song);

                //tableView.getItems().add(song);
            }
        });


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tableView);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        VBox songListPanel = new VBox(10);
        songListPanel.getChildren().add(scrollPane);
        root.setCenter(songListPanel);
    }


    private Button createBuyButton() {
        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M12 0C5.373 0 0 5.373 0 12C0 18.627 5.373 24 12 24C18.627 24 24 18.627 24 12C24 5.373 18.627 0 12 0ZM11 17L7 13H9V9H13V13H15L11 17Z");
        svgPath.setFill(Color.BLACK);

        Button buyButton = new Button();
        buyButton.setGraphic(svgPath);
        buyButton.setStyle("-fx-background-color: transparent;");
        //buyButton.setOnAction(e -> handlePurchase(song));

        return buyButton;
    }

    private void handlePurchase(Song song) {
        //Profile profile = new Profile();
        //Profile.addToCart(song);
        Profile.showCartWindow();
        System.out.println("Zakupiono utwór: " + song.getTitle());
    }

}



