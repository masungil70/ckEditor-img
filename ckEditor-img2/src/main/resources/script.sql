CREATE TABLE `file_token` (
	`token` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`status` INT(11) NOT NULL DEFAULT '0' COMMENT '0 : 임시, 1 : 사용 중 ',
	`make_date` DATETIME NULL DEFAULT current_timestamp(),
	PRIMARY KEY (`token`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;


CREATE TABLE `file_upload` (
	`file_id` INT(11) NOT NULL AUTO_INCREMENT,
	`token` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`original_filename` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`real_filename` VARCHAR(800) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`content_type` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`size` BIGINT(20) NULL DEFAULT NULL,
	`make_date` DATETIME NULL DEFAULT current_timestamp(),
	PRIMARY KEY (`file_id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1
;
