
CREATE TABLE `FREIGHT_UPDATE_DETAIL` (
  `uuid` varchar(128) NOT NULL,
  `pickup_allowance` decimal(9,2) DEFAULT NULL,
  `bracket_value` decimal(9,2) DEFAULT NULL,
  `bracket_type` int(11) DEFAULT NULL,
  `surcharge` decimal(9,2) DEFAULT NULL,
  `pu_percent` decimal(9,2) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `created_time` varchar(8) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `modified_time` varchar(8) DEFAULT NULL,
  `modified_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;