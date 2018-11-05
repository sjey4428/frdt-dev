
CREATE TABLE `SHIP_POINT_DETAIL` (
  `ship_point_no` int(11) NOT NULL,
  `uuid` varchar(128) NOT NULL,
  `opco_no` varchar(5) NOT NULL,
  `type` varchar(30) NOT NULL,
  `opco_name` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `zip_code` varchar(30) DEFAULT NULL,
  `sysco_currently_picksup` varchar(1) DEFAULT NULL,
  `distance_in_miles` decimal(9,2) DEFAULT NULL,
  `temp_code` varchar(30) DEFAULT NULL,
  `linehaul_rate` decimal(9,2) DEFAULT NULL,
  `fuel_surcharge_rate` decimal(9,2) DEFAULT NULL,
  `stop_charge` decimal(9,2) DEFAULT NULL,
  `unload_charge` decimal(9,2) DEFAULT NULL,
  `pickup_allowance_uom` varchar(30) DEFAULT NULL,
  `pickup_allowance` decimal(9,2) DEFAULT NULL,
  `freight_rate_uom` varchar(30) DEFAULT NULL,
  `bracket1` decimal(9,2) DEFAULT NULL,
  `bracket2` decimal(9,2) DEFAULT NULL,
  `bracket3` decimal(9,2) DEFAULT NULL,
  `bracket4` decimal(9,2) DEFAULT NULL,
  `bracket5` decimal(9,2) DEFAULT NULL,
  `bracket6` decimal(9,2) DEFAULT NULL,
  `bracket7` decimal(9,2) DEFAULT NULL,
  `bracket8` decimal(9,2) DEFAULT NULL,
  `bracket9` decimal(9,2) DEFAULT NULL,
  `bracket10` decimal(9,2) DEFAULT NULL,
  `brackets_uom` varchar(30) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `created_time` varchar(8) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `modified_time` varchar(8) DEFAULT NULL,
  `modified_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ship_point_no`,`uuid`,`opco_no`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
