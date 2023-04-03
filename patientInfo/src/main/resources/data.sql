/* Data to test the PatientInfo DB */

USE mediscreen;

-- Default values for table 'user'

INSERT INTO patient (family,given,dob,sex,address,phone)
VALUES
('last1','first1','2001-01-01','F','Address1','111111'),
('last2','first2','2002-02-02','M','Address2','222222'),
('last3','first3','2003-03-03','F','Address3','333333'),
('last3','first3','2004-04-04','F','Address4','444444');

