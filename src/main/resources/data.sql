INSERT INTO CUSTOMER(name) VALUES('COMPANY 1');
INSERT INTO CUSTOMER(name) VALUES('COMPANY 2');
INSERT INTO CUSTOMER(name) VALUES('COMPANY 3');

INSERT INTO DEVICE_TYPE(name) VALUES('Windows Workstation');
INSERT INTO DEVICE_TYPE(name) VALUES('Windows Server');
INSERT INTO DEVICE_TYPE(name) VALUES('Mac');

INSERT INTO DEVICE(system_name, device_type_id, customer_id) VALUES('Windows 1', 1, 1);
INSERT INTO DEVICE(system_name, device_type_id, customer_id) VALUES('Windows 2', 2, 1);
INSERT INTO DEVICE(system_name, device_type_id, customer_id) VALUES('Mac 1', 3, 1);
INSERT INTO DEVICE(system_name, device_type_id, customer_id) VALUES('Mac 2', 3, 1);
INSERT INTO DEVICE(system_name, device_type_id, customer_id) VALUES('Mac 3', 3, 1);

INSERT INTO SERVICE(service, cost) VALUES('Windows Antivirus', 5);
INSERT INTO SERVICE(service, cost) VALUES('Mac Antivirus', 7);
INSERT INTO SERVICE(service, cost) VALUES('Backup', 3);
INSERT INTO SERVICE(service, cost) VALUES('PSA', 2);
INSERT INTO SERVICE(service, cost) VALUES('Screen Share', 1);

INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(1, 1);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(3, 1);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(5, 1);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(1, 2);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(3, 2);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(5, 2);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(2, 3);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(3, 3);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(5, 3);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(2, 4);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(3, 4);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(5, 4);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(2, 5);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(3, 5);
INSERT INTO DEVICE_SERVICE(service_id, device_id) VALUES(5, 5);

INSERT INTO SAMPLE VALUES('1', 'VALUE1');
INSERT INTO SAMPLE VALUES('2', 'VALUE2');
