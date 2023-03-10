/* Data to test the PatientInfo DB */

USE mediscreen;

-- Default values for table 'user'

INSERT INTO patient (family,given,dob,sex,address,phone)
VALUES
('Last1','First1','2001-01-01','F','Address1','111111'),
('Last2','First2','2002-02-02','M','Address2','222222'),
('Last3','First3','2003-03-03','F','Address3','333333'),
('Last3','First3','2004-04-04','F','Address4','444444');

