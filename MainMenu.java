// Importy
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.geom.RoundRectangle2D;

public class MainMenu extends JFrame {
    public JPanel mainPanel;
    public boolean isDarkTheme = false;
    public LanguageManager languageManager;
    private final Map<JButton, String> buttonTextMap = new HashMap<>();
    private JToolBar toolBar;
    private JLabel logoLabel;
    private JLabel jbtsoundLabel;
    private JButton utworyButton;
    private JButton changeThemeButton;
    private JButton changeLanguageButton;
    private JButton regulationsButton; // Nowy przycisk regulamin
    private JLabel titleLabel; // Tytuł "Biggest Hits"

    public MainMenu() {
        setTitle("JBT Sound");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        languageManager = new LanguageManager();

        // Główny panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Pasek narzędzi na górze
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // Logo i napis JBTSound
        ImageIcon logoIcon = new ImageIcon("logoo.png"); // Upewnij się, że podajesz poprawną ścieżkę
        Image logoImage = logoIcon.getImage();
        Image resizedLogoImage = logoImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(resizedLogoImage));
        logoLabel.setPreferredSize(new Dimension(50, 50));

        jbtsoundLabel = new JLabel("JBTSound");
        jbtsoundLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Zmieniona czcionka i rozmiar
        jbtsoundLabel.setForeground(new Color(70, 130, 180)); // Kolor tekstu - subtelny, ale widoczny

        // Przyciski
        utworyButton = createToolBarButton("menu.utwory", "🎵");
        changeThemeButton = createToolBarButton("menu.theme", "🌙");
        changeLanguageButton = createToolBarButton("menu.language", "🌍");
        regulationsButton = createToolBarButton("menu.regulations", "📜"); // Dodajemy nowy przycisk

        // Dodanie elementów do paska narzędzi
        toolBar.add(logoLabel);
        toolBar.add(Box.createHorizontalStrut(15)); // Odstęp między logo a napisem
        toolBar.add(jbtsoundLabel);
        toolBar.add(Box.createHorizontalStrut(10)); // Odstęp między napisem a przyciskami
        toolBar.add(utworyButton);
        toolBar.add(changeThemeButton);
        toolBar.add(changeLanguageButton);
        toolBar.add(regulationsButton);

        // Dodanie paska narzędzi do głównego panelu
        mainPanel.add(toolBar, BorderLayout.NORTH);

        // Panel dolny z tytułem i kwadratami
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tytuł "Biggest Hits" z efektami
        titleLabel = new JLabel(languageManager.getText("menu.biggest.hits"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40)); // Zmieniony rozmiar czcionki
        titleLabel.setForeground(new Color(70, 130, 180)); // Kolor tekstu - subtelny, ale widoczny
        titleLabel.setOpaque(true);

        bottomPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel z kwadratami
        JPanel blocksPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        blocksPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        blocksPanel.setOpaque(false);

        // Dodanie mniejszych kwadratów z zaokrąglonymi rogami
        blocksPanel.add(createSquareBlock());
        blocksPanel.add(createSquareBlock());
        blocksPanel.add(createSquareBlock());
        blocksPanel.add(createSquareBlock());

        bottomPanel.add(blocksPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Akcje przycisków
        changeThemeButton.addActionListener(e -> showThemeMenu());
        changeLanguageButton.addActionListener(e -> showLanguageMenu());
        utworyButton.addActionListener(e -> showSongMenu());
        regulationsButton.addActionListener(e -> showRegulations()); // Akcja dla przycisku regulamin

        applyTheme();
        updateTexts();
        setVisible(true);
    }

    private void showRegulations() {
        // Wybór odpowiedniego pliku w zależności od języka
        String fileName = languageManager.isEnglish() ? "regulamin_en.txt" : "regulamin.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder regulationsContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                regulationsContent.append(line).append("\n");
            }

            // Tworzymy JTextArea do wyświetlania regulaminu
            JTextArea textArea = new JTextArea(regulationsContent.toString());
            textArea.setEditable(false);  // Tekst nie będzie edytowalny
            textArea.setLineWrap(true);   // Automatyczne zawijanie linii
            textArea.setWrapStyleWord(true);  // Zawijanie tekstu przy słowach
            textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));  // Czcionka zgodna z motywami
            textArea.setCaretPosition(0);  // Ustawiamy kursor na początek

            // Ustawiamy kolory na podstawie motywu
            if (isDarkTheme) {
                textArea.setBackground(new Color(50, 50, 50));  // Ciemne tło
                textArea.setForeground(new Color(255, 255, 255));  // Jasny tekst
            } else {
                textArea.setBackground(Color.WHITE);  // Jasne tło
                textArea.setForeground(Color.BLACK);  // Ciemny tekst
            }

            // Ustawiamy scrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));  // Szerokość i wysokość okna
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Dodanie marginesów wewnętrznych

            // Tworzymy niestandardowe okno JDialog
            JDialog regulationsDialog = new JDialog(this, languageManager.getText("menu.regulations"), true);
            regulationsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);  // Zamknięcie okna po naciśnięciu "X"

            // Przycisk "Zamknij"
            JButton closeButton = new JButton(languageManager.getText("menu.cancel"));  // Możesz dodać odpowiedni tekst
            closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            closeButton.addActionListener(e -> regulationsDialog.dispose());  // Zamykanie okna po kliknięciu

            // Panel przycisków
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(closeButton);  // Dodajemy przycisk do panelu

            // Tworzymy główny panel okna
            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new BorderLayout());
            dialogPanel.add(scrollPane, BorderLayout.CENTER);  // Panel z tekstem
            dialogPanel.add(buttonPanel, BorderLayout.SOUTH);  // Panel z przyciskiem

            regulationsDialog.getContentPane().add(dialogPanel);

            // Usuwamy 'setUndecorated(true)' żeby przywrócić standardowe ramki
            regulationsDialog.pack();
            regulationsDialog.setLocationRelativeTo(this);
            regulationsDialog.setVisible(true);  // Wyświetlenie okna

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    languageManager.getText("menu.regulations.error"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



    private JPanel createSquareBlock() {
        JPanel blockPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isDarkTheme ? new Color(60, 60, 60) : new Color(173, 216, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        blockPanel.setPreferredSize(new Dimension(100, 100));
        blockPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return blockPanel;
    }

    private JButton createToolBarButton(String textKey, String icon) {
        String text = languageManager.getText(textKey);
        JButton button = new JButton(icon + " " + text);
        buttonTextMap.put(button, textKey);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }


    private void showSongMenu() {
        JPopupMenu songMenu = new JPopupMenu();

        JMenuItem addSongItem = new JMenuItem(languageManager.getText("menu.add.song"));
        addSongItem.addActionListener(e -> addSong());
        songMenu.add(addSongItem);

        JMenuItem removeSongItem = new JMenuItem(languageManager.getText("menu.remove.song"));
        removeSongItem.addActionListener(e -> removeSong());
        songMenu.add(removeSongItem);

        JMenuItem createAlbumItem = new JMenuItem(languageManager.getText("menu.create.album"));
        createAlbumItem.addActionListener(e -> createAlbum());
        songMenu.add(createAlbumItem);

        JMenuItem songListItem = new JMenuItem(languageManager.getText("menu.song.list"));
        songListItem.addActionListener(e -> showSongList());
        songMenu.add(songListItem);

        songMenu.show(utworyButton, 0, utworyButton.getHeight());
    }

    private void addSong() {
        String songName = JOptionPane.showInputDialog(this, languageManager.getText("menu.enter.song.name"));
        if (songName != null && !songName.isEmpty()) {
            JOptionPane.showMessageDialog(this, languageManager.getText("menu.song.added") + ": " + songName);
        }
    }

    private void removeSong() {
        String songName = JOptionPane.showInputDialog(this, languageManager.getText("menu.enter.song.name.to.remove"));
        if (songName != null && !songName.isEmpty()) {
            JOptionPane.showMessageDialog(this, languageManager.getText("menu.song.removed") + ": " + songName);
        }
    }

    private void createAlbum() {
        String albumName = JOptionPane.showInputDialog(this, languageManager.getText("menu.enter.album.name"));
        if (albumName != null && !albumName.isEmpty()) {
            JOptionPane.showMessageDialog(this, languageManager.getText("menu.album.created") + ": " + albumName);
        }
    }

    private void showSongList() {
        JOptionPane.showMessageDialog(this, languageManager.getText("menu.song.list.display"));
    }

    private void showLanguageMenu() {
        JPopupMenu languageMenu = new JPopupMenu();

        JMenuItem englishItem = new JMenuItem("English");
        englishItem.addActionListener(e -> changeLanguage(true));
        languageMenu.add(englishItem);

        JMenuItem polishItem = new JMenuItem("Polski");
        polishItem.addActionListener(e -> changeLanguage(false));
        languageMenu.add(polishItem);

        languageMenu.show(changeLanguageButton, 0, changeLanguageButton.getHeight());
    }

    private void changeLanguage(boolean isEnglish) {
        languageManager.setLanguage(isEnglish);
        updateTexts();
    }

    private void showThemeMenu() {
        JPopupMenu themeMenu = new JPopupMenu();

        JMenuItem lightItem = new JMenuItem(languageManager.getText("theme.light"));
        lightItem.addActionListener(e -> changeTheme(false));
        themeMenu.add(lightItem);

        JMenuItem darkItem = new JMenuItem(languageManager.getText("theme.dark"));
        darkItem.addActionListener(e -> changeTheme(true));
        themeMenu.add(darkItem);

        themeMenu.show(changeThemeButton, 0, changeThemeButton.getHeight());
    }

    private void changeTheme(boolean isDark) {
        isDarkTheme = isDark;
        applyTheme();
    }

    private void applyTheme() {
        if (isDarkTheme) {
            mainPanel.setBackground(new Color(30, 30, 30)); // Dark background for the main panel
            toolBar.setBackground(new Color(45, 45, 45)); // Dark toolbar
            jbtsoundLabel.setForeground(new Color(204, 204, 204)); // Light grey text
            titleLabel.setForeground(new Color(204, 204, 204)); // Light grey title



            // Usuwamy obramowanie przycisków
            changeThemeButton.setBorderPainted(false);
            changeLanguageButton.setBorderPainted(false);
            utworyButton.setBorderPainted(false);
            regulationsButton.setBorderPainted(false);

            // Usuwamy tło przycisków, aby były tylko tekstowe
            changeThemeButton.setContentAreaFilled(false);
            changeLanguageButton.setContentAreaFilled(false);
            utworyButton.setContentAreaFilled(false);
            regulationsButton.setContentAreaFilled(false);

            // Kolor tekstu przycisków w ciemnym motywie
            changeThemeButton.setForeground(new Color(204, 204, 204)); // Light text for dark theme
            changeLanguageButton.setForeground(new Color(204, 204, 204));
            utworyButton.setForeground(new Color(204, 204, 204));
            regulationsButton.setForeground(new Color(204, 204, 204));

            // Interaktywność: zmiana koloru tekstu przy najechaniu
            changeThemeButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    changeThemeButton.setForeground(new Color(255, 250, 240));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    changeThemeButton.setForeground(new Color(204, 204, 204)); // Default color
                }
            });

            // Analogicznie dla innych przycisków:
            changeLanguageButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    changeLanguageButton.setForeground(new Color(255, 250, 240));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    changeLanguageButton.setForeground(new Color(204, 204, 204)); // Default color
                }
            });

            utworyButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    utworyButton.setForeground(new Color(255, 250, 240));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    utworyButton.setForeground(new Color(204, 204, 204)); // Default color
                }
            });

            regulationsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    regulationsButton.setForeground(new Color(255, 250, 240));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    regulationsButton.setForeground(new Color(204, 204, 204)); // Default color
                }
            });

        } else {
            // Jasny motyw
            mainPanel.setBackground(new Color(255, 255, 255)); // Very light background
            toolBar.setBackground(new Color(240, 240, 240)); // Light grayish toolbar
            jbtsoundLabel.setForeground(new Color(100, 100, 100)); // Soft dark gray text
            titleLabel.setForeground(new Color(100, 100, 100)); // Soft dark gray for the title



            // Usuwamy obramowanie przycisków
            changeThemeButton.setBorderPainted(false);
            changeLanguageButton.setBorderPainted(false);
            utworyButton.setBorderPainted(false);
            regulationsButton.setBorderPainted(false);

            // Usuwamy tło przycisków, aby były tylko tekstowe
            changeThemeButton.setContentAreaFilled(false);
            changeLanguageButton.setContentAreaFilled(false);
            utworyButton.setContentAreaFilled(false);
            regulationsButton.setContentAreaFilled(false);

            // Kolor tekstu przycisków w jasnym motywie
            changeThemeButton.setForeground(new Color(60, 60, 60)); // Dark gray text for light theme
            changeLanguageButton.setForeground(new Color(60, 60, 60));
            utworyButton.setForeground(new Color(60, 60, 60));
            regulationsButton.setForeground(new Color(60, 60, 60));

            // Interaktywność: zmiana koloru tekstu przy najechaniu
            changeThemeButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    changeThemeButton.setForeground(new Color(70, 130, 180));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    changeThemeButton.setForeground(new Color(60, 60, 60)); // Default color
                }
            });

            // Analogicznie dla innych przycisków:
            changeLanguageButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    changeLanguageButton.setForeground(new Color(70, 130, 180));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    changeLanguageButton.setForeground(new Color(60, 60, 60)); // Default color
                }
            });

            utworyButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    utworyButton.setForeground(new Color(70, 130, 180));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    utworyButton.setForeground(new Color(60, 60, 60)); // Default color
                }
            });

            regulationsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    regulationsButton.setForeground(new Color(70, 130, 180));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    regulationsButton.setForeground(new Color(60, 60, 60)); // Default color
                }
            });
        }
        SwingUtilities.updateComponentTreeUI(this); // Update theme changes
    }


    private void updateTexts() {
        // Update button texts based on the current language
        changeThemeButton.setText(languageManager.getText("menu.theme"));
        regulationsButton.setText(languageManager.getText("menu.regulations"));

        changeLanguageButton.setText(languageManager.getText("menu.language"));
        utworyButton.setText(languageManager.getText("menu.utwory"));

        // Update the title text based on the current language
        titleLabel.setText(languageManager.getText("menu.biggest.hits"));
    }


    public static void main(String[] args) {
        new MainMenu();
    }
}


class LanguageManager {
    private boolean isEnglish = true;

    // Metoda publiczna, która zwróci wartość isEnglish
    public boolean isEnglish() {
        return isEnglish;
    }

    public String getText(String key) {
        return switch (key) {
            case "menu.utwory" -> isEnglish ? "Songs" : "Utwory";
            case "menu.regulations" -> isEnglish ? "Regulations" : "Regulamin";
            case "menu.regulations.error" -> isEnglish ? "Unable to load regulations file." : "Nie można załadować pliku regulaminu.";
            case "menu.language" -> isEnglish ? "Language" : "Język";
            case "menu.theme" -> isEnglish ? "Theme" : "Motyw";
            case "menu.biggest.hits" -> isEnglish ? "Biggest Hits" : "Największe Hity";
            case "menu.add.song" -> isEnglish ? "Add Song" : "Dodaj Utwór";
            case "menu.remove.song" -> isEnglish ? "Remove Song" : "Usuń Utwór";
            case "menu.create.album" -> isEnglish ? "Create Album" : "Utwórz Album";
            case "menu.song.list" -> isEnglish ? "Song List" : "Lista Utworów";
            case "menu.cancel" -> isEnglish ? "Close" : "Zamknij";
            case "menu.select.option" -> isEnglish ? "Select an Option" : "Wybierz Opcję";
            case "menu.enter.song.name" -> isEnglish ? "Enter the song name:" : "Wpisz nazwę utworu:";
            case "menu.enter.song.name.to.remove" -> isEnglish ? "Enter the name of the song to remove:" : "Wpisz nazwę utworu do usunięcia:";
            case "menu.enter.album.name" -> isEnglish ? "Enter the album name:" : "Wpisz nazwę albumu:";
            case "menu.song.added" -> isEnglish ? "Song added" : "Utwór dodany";
            case "menu.song.removed" -> isEnglish ? "Song removed" : "Utwór usunięty";
            case "menu.album.created" -> isEnglish ? "Album created" : "Album utworzony";
            case "menu.song.list.display" -> isEnglish ? "Displaying the list of songs" : "Wyświetlanie listy utworów";
            case "theme.light" -> isEnglish ? "Light" : "Jasny";
            case "theme.dark" -> isEnglish ? "Dark" : "Ciemny";


            default -> key;
        };
    }

    public void setLanguage(boolean isEnglish) {
        this.isEnglish = isEnglish;
    }
}
