import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
    private JLabel titleLabel; // Tytuł "Biggest Hits"

    public MainMenu() {
        setTitle("Music Studio");
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
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT)); // Elementy wyrównane do lewej

        // Logo i napis JBTSound
        ImageIcon logoIcon = new ImageIcon("logo.png"); // Upewnij się, że podajesz poprawną ścieżkę
        Image logoImage = logoIcon.getImage();
        Image resizedLogoImage = logoImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(resizedLogoImage));
        logoLabel.setPreferredSize(new Dimension(50, 50));

        jbtsoundLabel = new JLabel("JBTSound");
        jbtsoundLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Zmieniona czcionka i rozmiar
        jbtsoundLabel.setForeground(new Color(70, 130, 180)); // Kolor tekstu - subtelny, ale widoczny

        // Przyciski
        utworyButton = createToolBarButton("menu.utwory", "🎵");
        changeThemeButton = createToolBarButton("menu.theme", "🌙");
        changeLanguageButton = createToolBarButton("menu.language", "🌍");

        // Dodanie elementów do paska narzędzi
        toolBar.add(logoLabel);
        toolBar.add(Box.createHorizontalStrut(10)); // Odstęp między logo a napisem
        toolBar.add(jbtsoundLabel);
        toolBar.add(Box.createHorizontalStrut(20)); // Odstęp między napisem a przyciskami
        toolBar.add(utworyButton);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(changeThemeButton);
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(changeLanguageButton);

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

        applyTheme();
        updateTexts();
        setVisible(true);
    }

    private JPanel createSquareBlock() {
        JPanel blockPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isDarkTheme ? new Color(60, 60, 60) : new Color(173, 216, 230)); // Jasnoniebieski dla jasnego, ciemniejszy dla ciemnego
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        blockPanel.setPreferredSize(new Dimension(100, 100));
        blockPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Dodajemy kursor ręki
        return blockPanel;
    }

    private JButton createToolBarButton(String textKey, String icon) {
        String text = languageManager.getText(textKey);
        JButton button = new JButton(icon + " " + text);
        buttonTextMap.put(button, textKey);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        enableButtonHoverEffect(button); // Enable hover effect
        return button;
    }

    private void enableButtonHoverEffect(JButton button) {
        button.setRolloverEnabled(true); // Enable rollover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isDarkTheme) {
                    button.setBackground(new Color(80, 80, 80)); // Dark hover effect
                } else {
                    button.setBackground(new Color(173, 216, 230)); // Light hover effect (light blue)
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Przywracanie oryginalnego koloru tła po opuszczeniu przycisku
                if (isDarkTheme) {
                    button.setBackground(new Color(60, 60, 60)); // Dark background color
                } else {
                    button.setBackground(new Color(70, 130, 180)); // Original light blue background color
                }
            }
        });
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
        updateTexts();  // This updates the language for the title as well
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
            changeThemeButton.setBackground(new Color(60, 60, 60)); // Dark button
            changeLanguageButton.setBackground(new Color(60, 60, 60));
            utworyButton.setBackground(new Color(60, 60, 60));
            changeThemeButton.setForeground(Color.WHITE);
            changeLanguageButton.setForeground(Color.WHITE);
            utworyButton.setForeground(Color.WHITE);
        } else {
            mainPanel.setBackground(new Color(255, 255, 255)); // Light background for the main panel
            toolBar.setBackground(new Color(240, 240, 240)); // Light toolbar
            jbtsoundLabel.setForeground(new Color(70, 130, 180)); // Blueish text for logo
            titleLabel.setForeground(new Color(70, 130, 180)); // Blueish title text
            changeThemeButton.setBackground(new Color(70, 130, 180)); // Niebieski przycisk w jasnym trybie
            changeLanguageButton.setBackground(new Color(70, 130, 180));
            utworyButton.setBackground(new Color(70, 130, 180)); // Niebieski przycisk
            changeThemeButton.setForeground(Color.WHITE);
            changeLanguageButton.setForeground(Color.WHITE);
            utworyButton.setForeground(Color.WHITE);
        }
        SwingUtilities.updateComponentTreeUI(this); // Update theme changes
    }

    private void updateTexts() {
        // Update button texts based on the current language
        changeThemeButton.setText(languageManager.getText("menu.theme"));
        changeLanguageButton.setText(languageManager.getText("menu.language"));
        utworyButton.setText(languageManager.getText("menu.utwory"));

        // Update the title text based on the current language
        titleLabel.setText(languageManager.getText("menu.biggest.hits"));
    }


    public static void main(String[] args) {
        new MainMenu();
    }
}


// LanguageManager class definition
class LanguageManager {
    private boolean isEnglish = true;

    public String getText(String key) {
        return switch (key) {
            case "menu.utwory" -> isEnglish ? "Songs" : "Utwory";
            case "menu.language" -> isEnglish ? "Language" : "Język";
            case "menu.theme" -> isEnglish ? "Theme" : "Motyw";
            case "menu.biggest.hits" -> isEnglish ? "Biggest Hits" : "Największe Hity";
            case "menu.add.song" -> isEnglish ? "Add Song" : "Dodaj Utwór";
            case "menu.remove.song" -> isEnglish ? "Remove Song" : "Usuń Utwór";
            case "menu.create.album" -> isEnglish ? "Create Album" : "Utwórz Album";
            case "menu.song.list" -> isEnglish ? "Song List" : "Lista Utworów";
            case "menu.cancel" -> isEnglish ? "Cancel" : "Anuluj";
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