
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 07, 2015 at 12:36 PM
-- Server version: 5.1.61
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u703673749_uom`
--

-- --------------------------------------------------------

--
-- Table structure for table `DB_Information`
--

CREATE TABLE IF NOT EXISTS `DB_Information` (
  `DB_ID` int(255) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Location` varchar(1024) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Unknown',
  `DataType` varchar(1024) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Unknown',
  `Units` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Unknown',
  `WebID` varchar(1024) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Unknown',
  `ID` int(255) NOT NULL,
  `Status` varchar(1024) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Offline',
  `XCoordinates` double NOT NULL,
  `YCoordinates` double NOT NULL,
  PRIMARY KEY (`DB_ID`),
  UNIQUE KEY `DB_ID` (`DB_ID`),
  UNIQUE KEY `DB_ID_3` (`DB_ID`),
  KEY `DB_ID_2` (`DB_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=30 ;

--
-- Dumping data for table `DB_Information`
--

INSERT INTO `DB_Information` (`DB_ID`, `Name`, `Location`, `DataType`, `Units`, `WebID`, `ID`, `Status`, `XCoordinates`, `YCoordinates`) VALUES
(1, 'BA:ACTIVE.1', 'BA', 'Active Power', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhACQAAAAUElTRVJWRVIxXEJBOkFDVElWRS4x', 9, 'Online', 0, 0),
(2, 'BA:CONC.1', 'BA', 'Unknown', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhACAAAAAUElTRVJWRVIxXEJBOkNPTkMuMQ', 8, 'Online', 0, 0),
(3, 'BA:LEVEL.1', 'BA', 'Unknown', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhABwAAAAUElTRVJWRVIxXEJBOkxFVkVMLjE', 7, 'Online', 0, 0),
(4, 'BA:PHASE.1', 'BA', 'Phase', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhACgAAAAUElTRVJWRVIxXEJBOlBIQVNFLjE', 10, 'Online', 0, 0),
(5, 'BA:TEMP.1', 'BA', 'Temperature', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhABgAAAAUElTRVJWRVIxXEJBOlRFTVAuMQ', 6, 'Online', 0, 0),
(6, 'BRID1.CurrentA', 'BRID1', 'Current A', 'A', 'P0P3M-CGJlpkKFXS0yV-1NhAPAAAAAUElTRVJWRVIxXEJSSUQxLkNVUlJFTlRB', 60, 'Online', 0, 0),
(7, 'BRID1.CurrentB', 'BRID1', 'Current B', 'A', 'P0P3M-CGJlpkKFXS0yV-1NhAPQAAAAUElTRVJWRVIxXEJSSUQxLkNVUlJFTlRC', 61, 'Online', 0, 0),
(8, 'BRID1.CurrentC', 'BRID1', 'Current C', 'A', 'P0P3M-CGJlpkKFXS0yV-1NhAPgAAAAUElTRVJWRVIxXEJSSUQxLkNVUlJFTlRD', 62, 'Online', 0, 0),
(9, 'BRID1.Frequency', 'BRID1', 'Frequency', 'Hz', 'P0P3M-CGJlpkKFXS0yV-1NhAEQAAAAUElTRVJWRVIxXEJSSUQxLkZSRVFVRU5DWQ', 17, 'Online', 0, 0),
(10, 'BRID1.PowerA', 'BRID1', 'Power A', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAFAAAAAUElTRVJWRVIxXEJSSUQxLlBPV0VSQQ', 20, 'Online', 0, 0),
(11, 'BRID1.PowerB', 'BRID1', 'Power B', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhANwAAAAUElTRVJWRVIxXEJSSUQxLlBPV0VSQg', 55, 'Online', 0, 0),
(12, 'BRID1.PowerC', 'BRID1', 'Power C', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAOAAAAAUElTRVJWRVIxXEJSSUQxLlBPV0VSQw', 56, 'Online', 0, 0),
(13, 'BRID1.ReactivePowerA', 'BRID1', 'Reactive Power A', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAOwAAAAUElTRVJWRVIxXEJSSUQxLlJFQUNUSVZFUE9XRVJB', 59, 'Online', 0, 0),
(14, 'BRID1.ReactivePowerB', 'BRID1', 'Reactive Power B', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAOgAAAAUElTRVJWRVIxXEJSSUQxLlJFQUNUSVZFUE9XRVJC', 58, 'Online', 0, 0),
(15, 'BRID1.ReactivePowerC', 'BRID1', 'Reactive Power C', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAOQAAAAUElTRVJWRVIxXEJSSUQxLlJFQUNUSVZFUE9XRVJD', 57, 'Online', 0, 0),
(16, 'BRID1.VoltageA', 'BRID1', 'Voltage A', 'V', 'P0P3M-CGJlpkKFXS0yV-1NhAEAAAAAUElTRVJWRVIxXEJSSUQxLlZPTFRBR0VB', 16, 'Online', 0, 0),
(17, 'BRID1.VoltageB', 'BRID1', 'Voltage B', 'V', 'P0P3M-CGJlpkKFXS0yV-1NhAEgAAAAUElTRVJWRVIxXEJSSUQxLlZPTFRBR0VC', 18, 'Online', 0, 0),
(18, 'BRID1.VoltageC', 'BRID1', 'Voltage C', 'V', 'P0P3M-CGJlpkKFXS0yV-1NhANQAAAAUElTRVJWRVIxXEJSSUQxLlZPTFRBR0VD', 53, 'Online', 0, 0),
(19, 'Weather.Whitworth.Cloudbase', 'Whitworth', 'Cloudbase', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAIgAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILkNMT1VEQkFTRQ', 34, 'Online', 0, 0),
(20, 'Weather.Whitworth.CloudCover', 'Whitworth', 'Cloud Cover', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAJAAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILkNMT1VEQ09WRVI', 36, 'Online', 0, 0),
(21, 'Weather.Whitworth.DewPoint', 'Whitworth', 'Dew Point', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAGAAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILkRFV1BPSU5U', 24, 'Online', 0, 0),
(22, 'Weather.Whitworth.OpticalRange', 'Whitworth', 'Optical Range', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAHgAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILk9QVElDQUxSQU5HRQ', 30, 'Online', 0, 0),
(23, 'Weather.Whitworth.PrecipitationRate', 'Whitworth', 'Precipitation Rate', 'Unknown', '1NhAHAAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILlBSRUNJUElUQVRJT05SQVRF', 28, 'Online', 0, 0),
(24, 'Weather.Whitworth.Pressure', 'Whitworth', 'Pressure', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAFgAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILlBSRVNTVVJF', 22, 'Online', 0, 0),
(25, 'Weather.Whitworth.RelativeHumidity', 'Whitworth', 'Relative Humidity', 'Unknown', '1NhAFwAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILlJFTEFUSVZFSFVNSURJVFk', 23, 'Online', 0, 0),
(26, 'Weather.Whitworth.SolarRadiation', 'Whitworth', 'Solar Radiation', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAIwAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILlNPTEFSUkFESUFUSU9O', 35, 'Online', 0, 0),
(27, 'Weather.Whitworth.Temperature', 'Whitworth', 'Temperature', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAFQAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILlRFTVBFUkFUVVJF', 21, 'Online', 0, 0),
(28, 'Weather.Whitworth.WindDirection', 'Whitworth', 'Wind Direction', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAGgAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILldJTkRESVJFQ1RJT04', 26, 'Online', 0, 0),
(29, 'Weather.Whitworth.WindSpeed', 'Whitworth', 'Wind Speed', 'Unknown', 'P0P3M-CGJlpkKFXS0yV-1NhAGQAAAAUElTRVJWRVIxXFdFQVRIRVIuV0hJVFdPUlRILldJTkRTUEVFRA', 25, 'Online', 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
