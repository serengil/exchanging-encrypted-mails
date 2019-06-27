# Exchanging Encrypted Mails

This project provides exchanging encrypted mails on gmail infrastructure. 

In mail sending, Bob generates a random secret key, encrypts the secret key with Alice's public key
, attach this encrypted key into the mail, and also encrypts mail content with randomly generated secret key.

In mail reading, Alice uses her private key and decrypts attached encrypted secret key. 
Thus, she would be aware of randomly generated secret key because attached encrypted key is encrypted with her public key.

Moreover, mail content encrypted with different secret keys for every run.

Usage
=====

Run com.crypto.mail.MailSender.java for delivering mails, and run com.crypto.mail.MailReader.java for reading mails. 

The both file contains username and password varibables, change the content of these variables to execute.

Requirements
=====

Download Java Mail API (http://javamail.java.net/ ) and reference it. This implementation references javax.mail.jar 1.5.6 version.

Also, you need to allow less secure applications to access your gmail account (https://www.google.com/settings/security/lesssecureapps )

# License
=====

This repository is licensed under the MIT License - see [LICENSE](https://github.com/serengil/exchanging-encrypted-mails/blob/master/LICENSE) for more details.
