-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 10, 2025 at 04:33 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studio_nagran`
--

-- --------------------------------------------------------

--
-- Table structure for table `plyty`
--

CREATE TABLE `plyty` (
  `id` int(11) NOT NULL,
  `nazwa_plyty` varchar(200) NOT NULL,
  `utwor_id` int(11) DEFAULT NULL,
  `tworca_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `plyty`
--

INSERT INTO `plyty` (`id`, `nazwa_plyty`, `utwor_id`, `tworca_id`) VALUES
(1, 'Greatest Hits', 1, 1),
(2, 'Greatest Hits', 2, 1),
(3, 'Greatest Hits', 3, 1),
(4, 'Greatest Hits', 4, 1),
(5, 'Greatest Hits', 5, 1),
(6, 'The Classics', 6, 2),
(7, 'The Classics', 7, 2),
(8, 'The Classics', 8, 2),
(9, 'The Classics', 9, 2),
(10, 'The Classics', 10, 2),
(11, 'Legendary Tracks', 11, 3),
(12, 'Legendary Tracks', 12, 3),
(13, 'Legendary Tracks', 13, 3),
(14, 'Legendary Tracks', 14, 3),
(15, 'Legendary Tracks', 15, 3);

-- --------------------------------------------------------

--
-- Table structure for table `posiadane_plyty`
--

CREATE TABLE `posiadane_plyty` (
  `id` int(11) NOT NULL,
  `uzytkownik_id` int(11) DEFAULT NULL,
  `plyta_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `posiadane_utwory`
--

CREATE TABLE `posiadane_utwory` (
  `id` int(11) NOT NULL,
  `uzytkownik_id` int(11) DEFAULT NULL,
  `utwor_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tworcy`
--

CREATE TABLE `tworcy` (
  `id` int(11) NOT NULL,
  `pseudonim` varchar(100) NOT NULL,
  `imie_tworcy` varchar(30) DEFAULT NULL,
  `nazwisko_tworcy` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tworcy`
--

INSERT INTO `tworcy` (`id`, `pseudonim`, `imie_tworcy`, `nazwisko_tworcy`) VALUES
(1, 'ArtystkaX', 'Anna', 'Kowalska'),
(2, 'MuzykY', 'Jan', 'Nowak'),
(3, 'MelomanZ', 'Piotr', 'Wiśniewski'),
(4, 'RockStar', 'Katarzyna', 'Adamska'),
(5, 'JazzMaster', 'Michał', 'Zieliński'),
(6, 'DJ_Sound', 'Dominik', 'Kowal'),
(7, 'BeatMaker', 'Magdalena', 'Piasecka'),
(8, 'PianoPro', 'Agnieszka', 'Domańska'),
(9, 'GuitarHero', 'Tomasz', 'Mazur'),
(10, 'VocalKing', 'Jacek', 'Wiśniowski'),
(11, 'DrummerD', 'Karolina', 'Nowicka'),
(12, 'SingerS', 'Aleksander', 'Pawlak'),
(13, 'LyricistL', 'Ewa', 'Krawczyk'),
(14, 'ComposerC', 'Natalia', 'Zawadzka'),
(15, 'BassistB', 'Grzegorz', 'Ostrowski'),
(16, 'SynthStar', 'Olga', 'Malinowska'),
(17, 'FunkyF', 'Paweł', 'Rutkowski'),
(18, 'VocalV', 'Julia', 'Głowacka'),
(19, 'RapGod', 'Kamil', 'Woźniak'),
(20, 'SoulSinger', 'Sylwia', 'Jankowska');

-- --------------------------------------------------------

--
-- Table structure for table `utwory`
--

CREATE TABLE `utwory` (
  `id` int(11) NOT NULL,
  `nazwa_utworu` varchar(200) NOT NULL,
  `data_dodania` date NOT NULL,
  `tworca_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utwory`
--

INSERT INTO `utwory` (`id`, `nazwa_utworu`, `data_dodania`, `tworca_id`) VALUES
(1, 'Shape of You', '2023-01-01', 1),
(2, 'Blinding Lights', '2023-01-02', 2),
(3, 'Bohemian Rhapsody', '2023-01-03', 3),
(4, 'Hotel California', '2023-01-04', 4),
(5, 'Imagine', '2023-01-05', 5),
(6, 'Smells Like Teen Spirit', '2023-01-06', 6),
(7, 'Rolling in the Deep', '2023-01-07', 7),
(8, 'Someone Like You', '2023-01-08', 8),
(9, 'Billie Jean', '2023-01-09', 9),
(10, 'Stairway to Heaven', '2023-01-10', 10),
(11, 'Hallelujah', '2023-01-11', 11),
(12, 'Yesterday', '2023-01-12', 12),
(13, 'Hey Jude', '2023-01-13', 13),
(14, 'Wonderwall', '2023-01-14', 14),
(15, 'Sweet Child O Mine', '2023-01-15', 15),
(16, 'Let It Be', '2023-01-16', 16),
(17, 'What a Wonderful World', '2023-01-17', 17),
(18, 'Like a Rolling Stone', '2023-01-18', 18),
(19, 'Lose Yourself', '2023-01-19', 19),
(20, 'No Woman No Cry', '2023-01-20', 20);

-- --------------------------------------------------------

--
-- Table structure for table `uzytkownicy`
--

CREATE TABLE `uzytkownicy` (
  `id` int(99) NOT NULL,
  `nazwa_uzytkownika` varchar(30) NOT NULL,
  `haslo` varchar(255) NOT NULL,
  `permisje` int(9) NOT NULL,
  `email` varchar(30) NOT NULL,
  `zdjecie` blob DEFAULT NULL,
  `Saldo` int(99) DEFAULT NULL,
  `Imie` varchar(30) NOT NULL,
  `Nazwisko` varchar(30) NOT NULL,
  `Adres` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `uzytkownicy`
--

INSERT INTO `uzytkownicy` (`id`, `nazwa_uzytkownika`, `haslo`, `permisje`, `email`, `zdjecie`, `Saldo`, `Imie`, `Nazwisko`, `Adres`) VALUES
(1, 'admin', 'admin', 1, 'admin@admin', '', 500, 'admin', 'admin', 'admin'),
(2, 'Fikziq', 'Fikziq123', 2, 'Fikziq@gmail.com', '', 0, 'Conor', 'McGregor', 'Prawobrzeze '),
(3, 'Wilku', 'Wilku123', 2, 'WilkuFortnite@gmail.com', NULL, 7500, 'Kubus', 'thebill', 'Niebuszewo');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `plyty`
--
ALTER TABLE `plyty`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utwor_id` (`utwor_id`),
  ADD KEY `tworca_id` (`tworca_id`);

--
-- Indexes for table `posiadane_plyty`
--
ALTER TABLE `posiadane_plyty`
  ADD PRIMARY KEY (`id`),
  ADD KEY `uzytkownik_id` (`uzytkownik_id`),
  ADD KEY `plyta_id` (`plyta_id`);

--
-- Indexes for table `posiadane_utwory`
--
ALTER TABLE `posiadane_utwory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `uzytkownik_id` (`uzytkownik_id`),
  ADD KEY `utwor_id` (`utwor_id`);

--
-- Indexes for table `tworcy`
--
ALTER TABLE `tworcy`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `utwory`
--
ALTER TABLE `utwory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tworca_id` (`tworca_id`);

--
-- Indexes for table `uzytkownicy`
--
ALTER TABLE `uzytkownicy`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `plyty`
--
ALTER TABLE `plyty`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `posiadane_plyty`
--
ALTER TABLE `posiadane_plyty`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `posiadane_utwory`
--
ALTER TABLE `posiadane_utwory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tworcy`
--
ALTER TABLE `tworcy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `utwory`
--
ALTER TABLE `utwory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `uzytkownicy`
--
ALTER TABLE `uzytkownicy`
  MODIFY `id` int(99) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `plyty`
--
ALTER TABLE `plyty`
  ADD CONSTRAINT `plyty_ibfk_1` FOREIGN KEY (`utwor_id`) REFERENCES `utwory` (`id`),
  ADD CONSTRAINT `plyty_ibfk_2` FOREIGN KEY (`tworca_id`) REFERENCES `tworcy` (`id`);

--
-- Constraints for table `posiadane_plyty`
--
ALTER TABLE `posiadane_plyty`
  ADD CONSTRAINT `posiadane_plyty_ibfk_1` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownicy` (`id`),
  ADD CONSTRAINT `posiadane_plyty_ibfk_2` FOREIGN KEY (`plyta_id`) REFERENCES `plyty` (`id`);

--
-- Constraints for table `posiadane_utwory`
--
ALTER TABLE `posiadane_utwory`
  ADD CONSTRAINT `posiadane_utwory_ibfk_1` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownicy` (`id`),
  ADD CONSTRAINT `posiadane_utwory_ibfk_2` FOREIGN KEY (`utwor_id`) REFERENCES `utwory` (`id`);

--
-- Constraints for table `utwory`
--
ALTER TABLE `utwory`
  ADD CONSTRAINT `utwory_ibfk_1` FOREIGN KEY (`tworca_id`) REFERENCES `tworcy` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
