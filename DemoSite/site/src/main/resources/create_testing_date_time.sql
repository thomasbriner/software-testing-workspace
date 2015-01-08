-- SQL for manually creating testing_date_time  
  
CREATE TABLE `blc_testing_date_time` (
  `ID` int(11) NOT NULL,
  `TESTING_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `broadleaf`.`blc_testing_date_time` (`ID`, `TESTING_DATE`) VALUES ('1', '2014-01-10 08:00:01');

UPDATE `broadleaf`.`blc_testing_date_time` SET `TESTING_DATE`='2014-01-10 09:00:01' WHERE `ID`='1';
