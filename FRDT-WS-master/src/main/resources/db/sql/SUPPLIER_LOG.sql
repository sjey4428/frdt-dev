CREATE TABLE `SUPPLIER_LOG` (
  `uuid` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `user_name` varchar(128) DEFAULT NULL,
  `log_header` varchar(64) DEFAULT NULL,
  `log_desc` varchar(256) DEFAULT NULL,
  `verdor_uuid` varchar(128) DEFAULT NULL,
  `verdor_name` varchar(128) DEFAULT NULL,
  `oper_time` timestamp NULL DEFAULT NULL,
  `oper_result` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
