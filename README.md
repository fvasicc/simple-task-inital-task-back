Projekat se pokrece na portu 8080 -> <code>localhost:8080/</code>

U aplication.properties izmeniti sledece parametre tj. dodati mejl adresu i lozinku naloga sa kojeg ce se slati mejlovi
<code>
spring.mail.username=mail@gmail.com
spring.mail.password=password
</code>

Takodje je potrebno otkomentarisati linije 105 i 151 u klasi AppointmentService u paketu <code>com.dentist.demo.service</code>, da bi radilo slanje mejlova.

Linije su zakomentarisene jer je meni izbacivalo gresku, jer gugl ne dozvoljava pristup mejlu sa "nesigurnih" izvora, a do marta ove godine to vise nije moguce omoguciti rucno.
