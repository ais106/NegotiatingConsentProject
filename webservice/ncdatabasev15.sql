-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 09, 2015 at 01:08 PM
-- Server version: 5.5.44-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ncdatabase`
--
CREATE DATABASE IF NOT EXISTS `ncdatabase` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ncdatabase`;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

CREATE TABLE IF NOT EXISTS `answer` (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `date_answered` datetime DEFAULT NULL,
  `answer_option` text NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`answer_id`),
  KEY `fk_answer_question1_idx` (`question_id`),
  KEY `fk_answer_user1_idx` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=912 ;

-- --------------------------------------------------------

--
-- Table structure for table `app_monitor`
--

CREATE TABLE IF NOT EXISTS `app_monitor` (
  `app_tracking_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_tracked` datetime DEFAULT NULL,
  `log_file` blob,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`app_tracking_id`),
  KEY `fk_app_monitor_user1_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `group_question_request`
--
CREATE TABLE IF NOT EXISTS `group_question_request` (
`question_request_id` int(11)
,`request_type` enum('practice','survey','review')
,`request_day` int(11)
,`request_time` int(11)
,`question_id` int(11)
,`question_text` text
,`associated_action` text
,`available_points` int(11)
,`group_id` int(11)
,`group_name` text
);
-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_text` text,
  `associated_action` text,
  `available_points` int(11) DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

-- --------------------------------------------------------

--
-- Table structure for table `question_request`
--

CREATE TABLE IF NOT EXISTS `question_request` (
  `question_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `request_type` enum('practice','survey','review') NOT NULL DEFAULT 'survey',
  `request_day` int(11) NOT NULL,
  `request_time` int(11) NOT NULL,
  PRIMARY KEY (`question_request_id`),
  KEY `fk_question_request_user1_idx` (`group_id`),
  KEY `fk_question_request_question1_idx` (`question_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=446 ;

-- --------------------------------------------------------

--
-- Table structure for table `survey_group`
--

CREATE TABLE IF NOT EXISTS `survey_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` text,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `pass_key` text,
  `date_joined` datetime DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=741 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_data`
--

CREATE TABLE IF NOT EXISTS `user_data` (
  `user_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_collected` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `data_content` blob,
  PRIMARY KEY (`user_data_id`),
  KEY `fk_user_data_user1_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_question_request`
--
CREATE TABLE IF NOT EXISTS `user_question_request` (
`user_request_id` int(11)
,`user_id` int(11)
,`request_sent` tinyint(1)
,`request_type` enum('practice','survey','review')
,`request_day` int(11)
,`request_time` int(11)
,`question_id` int(11)
,`question_text` text
,`associated_action` text
,`available_points` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `user_request`
--

CREATE TABLE IF NOT EXISTS `user_request` (
  `user_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_request_id` int(11) NOT NULL,
  `request_sent` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_request_id`),
  KEY `fk_user_request_user1_idx` (`user_id`),
  KEY `fk_user_request_question_request1_idx` (`question_request_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=77266 ;

-- --------------------------------------------------------

--
-- Structure for view `group_question_request`
--
DROP TABLE IF EXISTS `group_question_request`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `group_question_request` AS select `question_request`.`question_request_id` AS `question_request_id`,`question_request`.`request_type` AS `request_type`,`question_request`.`request_day` AS `request_day`,`question_request`.`request_time` AS `request_time`,`question`.`question_id` AS `question_id`,`question`.`question_text` AS `question_text`,`question`.`associated_action` AS `associated_action`,`question`.`available_points` AS `available_points`,`survey_group`.`group_id` AS `group_id`,`survey_group`.`group_name` AS `group_name` from ((`question_request` join `question` on((`question_request`.`question_id` = `question`.`question_id`))) join `survey_group` on((`question_request`.`group_id` = `survey_group`.`group_id`)));

-- --------------------------------------------------------

--
-- Structure for view `user_question_request`
--
DROP TABLE IF EXISTS `user_question_request`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_question_request` AS select `user_request`.`user_request_id` AS `user_request_id`,`user_request`.`user_id` AS `user_id`,`user_request`.`request_sent` AS `request_sent`,`question_request`.`request_type` AS `request_type`,`question_request`.`request_day` AS `request_day`,`question_request`.`request_time` AS `request_time`,`question`.`question_id` AS `question_id`,`question`.`question_text` AS `question_text`,`question`.`associated_action` AS `associated_action`,`question`.`available_points` AS `available_points` from ((`user_request` join `question_request` on((`user_request`.`question_request_id` = `question_request`.`question_request_id`))) join `question` on((`question_request`.`question_id` = `question`.`question_id`)));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `fk_answer_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_answer_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `app_monitor`
--
ALTER TABLE `app_monitor`
  ADD CONSTRAINT `fk_app_monitor_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `question_request`
--
ALTER TABLE `question_request`
  ADD CONSTRAINT `fk_question_request_group_id` FOREIGN KEY (`group_id`) REFERENCES `survey_group` (`group_id`),
  ADD CONSTRAINT `fk_question_request_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `survey_group` (`group_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `user_data`
--
ALTER TABLE `user_data`
  ADD CONSTRAINT `fk_user_data_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_request`
--
ALTER TABLE `user_request`
  ADD CONSTRAINT `fk_user_request_question_request1` FOREIGN KEY (`question_request_id`) REFERENCES `question_request` (`question_request_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_request_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;