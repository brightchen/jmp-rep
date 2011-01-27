
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (1, 'admin');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (2, 'user');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (3, 'operator');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (4, 'editor');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (5, 'reviewer');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (6, 'marketmgr');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (7, 'ftpmgr');




-- added new one
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (8, 'sysadmin');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (9, 'sysuser');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (10, 'publishermgr');
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (11, 'subscribermgr');

Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (12, 'virtualpublisher' );
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (13, 'reportmgr' );
Insert into TB_ROLE (ROLE_ID, AUTHORITY) Values (14, 'accessmgr');



UPDATE TB_PUBLISHER SET enabled = 1;
-- update enable =1 tb_publisher_user after creating the SYSTEM user
-- update enable =1 tb_publisher for all user, remove "processing" from STATUS

COMMIT;





-- need to run this after creating the first Publication
Update TB_PUBLICATION set STYLES='AlIttihad' where publication_id=1;


ALTER TABLE TB_ACTION_LOG MODIFY SUBSCRIPTION_ID NUMBER(11) NULL;

ALTER TABLE TB_EDITION MODIFY EDITION_NAME VARCHAR2(64);


DROP SEQUENCE ROLE_ID_SEQUENCE;
DROP SEQUENCE PUSER_ID_SEQUENCE;
DROP SEQUENCE PUBLISHER_ID_SEQUENCE;
DROP SEQUENCE PUBLICATION_ID_SEQUENCE;
DROP SEQUENCE DOCUMENT_ID_SEQUENCE;
DROP SEQUENCE ASSET_ID_SEQUENCE;

CREATE SEQUENCE ROLE_ID_SEQUENCE START WITH 1 NOMAXVALUE NOCYCLE CACHE 10;
CREATE SEQUENCE PUSER_ID_SEQUENCE START WITH 1 NOMAXVALUE NOCYCLE CACHE 10;
CREATE SEQUENCE PUBLISHER_ID_SEQUENCE START WITH 1 NOMAXVALUE NOCYCLE CACHE 10;
CREATE SEQUENCE PUBLICATION_ID_SEQUENCE START WITH 1 NOMAXVALUE NOCYCLE CACHE 10;
CREATE SEQUENCE DOCUMENT_ID_SEQUENCE START WITH 1 NOMAXVALUE NOCYCLE CACHE 10;
CREATE SEQUENCE PEATEST.ASSET_ID_SEQUENCE START WITH 1 NOMAXVALUE NOCYCLE CACHE 10;













