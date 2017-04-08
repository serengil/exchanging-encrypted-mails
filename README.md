# exchanging-encrypted-mails

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

License
=======

Copyright 2016 Sefik Ilkin Serengil

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
