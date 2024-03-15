Storing JWTs securely

Storing JWTs securely in an Android device requires careful consideration to protect the token from unauthorized access, such as by malicious apps or attackers.

In this DEMO we will use SharedPreferences with Encryption.

@TODO:
Starting from Android 6.0 (API level 23), the Android Keystore system can securely generate and store cryptographic keys, 
which can then be used to encrypt sensitive information, including JWTs, before saving them in `SharedPreferences`.
