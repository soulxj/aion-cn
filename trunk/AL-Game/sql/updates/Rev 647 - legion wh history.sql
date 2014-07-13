ALTER TABLE `legion_history`
MODIFY COLUMN `history_type` enum('CREATE','JOIN','KICK','APPOINTED','EMBLEM_REGISTER','EMBLEM_MODIFIED','ITEM_DEPOSIT','ITEM_WITH_DRAW','KINAH_DOPOSIT','KINAH_WITH_DRAW','ITEM_WITHDRAW','KINAH_DEPOSIT','KINAH_WITHDRAW') NOT NULL;

UPDATE `legion_history` SET `history_type` = 'ITEM_WITHDRAW' WHERE `history_type` = 'ITEM_WITH_DRAW';
UPDATE `legion_history` SET `history_type` = 'KINAH_WITHDRAW' WHERE `history_type` = 'KINAH_WITH_DRAW';
UPDATE `legion_history` SET `history_type` = 'KINAH_DEPOSIT' WHERE `history_type` = 'KINAH_DOPOSIT';

ALTER TABLE `legion_history`
MODIFY COLUMN `history_type` enum('CREATE','JOIN','KICK','APPOINTED','EMBLEM_REGISTER','EMBLEM_MODIFIED','ITEM_DEPOSIT','ITEM_WITHDRAW','KINAH_DEPOSIT','KINAH_WITHDRAW') NOT NULL;