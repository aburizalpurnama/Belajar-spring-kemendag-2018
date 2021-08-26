--- Membuat aplikasi java web menggunakan spring ---

-> MEMBUAT PROJECT BARU
    - Buka start.spring.io kemudian pilih dependency web dan generate project.
    - Kemudian upload repository ke github
    - Kemudia ekstrak dan buka project menggunakan IDE

Didalam project yang sudah dibuat terdapat main class yang bisa langsung dijalankan kedalam web    server dengan cara menekan tombol run atau dengan command : mvn clean spring-boot:run

-> BUAT INDEX YANG AKAN MENJADI TAMPILAN UTAMA PADA APLIKASI
    - Buat file html dan ditaro di direktori resource/static.

    >> CARA GANTI PORT
        - Buka file konfigurasi yang ada di direktori resources/aplication.properties
        - Buat variable server.port=10000

-> BUAT CONTROLLER UNTUK MENANGANI HTTP REQUEST
    - Buat package baru bernama controller
    - Buat class dengan memberikan nama controller padaakhirannya. Kemudian berikan anotation @Controller untuk memberi tahu kepada spring bahwa class ini adalah sebuat controller.
    - Buat method yang akan dijalankan jika user mengakses url tertentu contohnya localhost:10000/waktu, kemudian beri annotation @GetMapping(/waktu) pada method tersebut untuk memberi tahu kepada framework spring.
    - Tambahkan annotation @ResponseBody pada method untuk method yang hanya mengembalikan nilai.

        >> Jika menemukan Error 404 Not Found, bisa jadi karena belum mendeklarasikan annotation @Controller

-> MEMBUAT OUTPUT JSON
    - Buat method baru dengan return sebuah objek Map<String, String> yang akan menjadi JSON dengan parameter String nama
    - Beri annotation @GetMapping("/appinfo")
    - Kemudian pada parameter berikan annotation @RequestParam untuk memberitahu kepada spring bahwa parameter ini diinput oleh user. Jika user tidak menyertakan parameter yang diminta, maka akan terjadi error 400 dengan status bad request.
    - Kemudian jangan lupa kasih juga @ResposeBody
 
        >> Untuk membuat format json lebih terlihat rapih, tambahkan 'spring.jackson.serialization.indent_output=true' pada application.properties
        >> Objek LinkedHashMap<t,t> akan terjaga urutannya, akan tetapi lambat dalam melakukan pencarian

-> MEMBUAT WEB SERVICE
    - membuat xsd dari xml yang sudah dibuat dengan menggunakan xsd geratator online 'www.freeformatter.com/xsd-generator.html'
    - buat file xsd di direktori resources dan masukkan semua code xsd yang sudah digenerate.
    - tambahkan library spring-boot-starter-web-service dan wsdl4j kedalam pom.xml
    - Tambahkan plugin pada pom.xml untuk mengenerate class dari file xsd yang sudah dibuat.
        ...
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
                <artifactId>jaxb2-maven-plugin</artifactId>
                <version>2.5.0</version>
                <executions>
                    <execution>
                        <id>xjc</id>
                        <goals>
                            <goal>xjc</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <sources>
                        <source>${project.basedir}/src/main/resources/countries.xsd</source>
                    </sources>
                </configuration>
        </plugin>
        ...

        >> Kelebihan jika menggunakan PreparedStatement.
            1. Terbebas dari SQL Injection
            2. Lebih Optimal karena setap sql di-cache sehingga tidak perlu melakukan semuanya dari awal.

        >> ORM (Object Relational Mapping)

            Fungsi-fungsi :
                - Konversi dari ResultSet menjadi object.
                - Generate SQL untuk tiap merek/versi Database
                - Mapping antara tabel dan database.
                - Optimasi Eksekusi SQL
                - Cahching perintah SQL

            Mitos ORM :
                - Lebih lambat : tapi tidak signifikan dan mash acceptable.
                - SQL tidak optimal : belum tentu, karena pembuat konsep ORM lebih paham akan database jika dibandingkan programmer yang haya menggunakan database.

            Produk-produk ORM populer :
                - Hibernate (JBos - RedHat)
                - Toplink (Oracle -  Berbayar)
                - EclipseLink (Oracle - Gratis)

        >> Connection & Transaction Managemnent

            fungsi utama : 
             - Connection Pooling
                * Min idle : jumlah minimum yang tetap connect walaupun sengan idle.
                * max active : jumlah makssimum koneksi yang boleh dibuat.
                * Idle timeout :  waktu idle maksimal sebelum disconnect
                * max wait  : batas waktu menunggu koneksi tersedia, sehingga dapat dilemparkan pesan error dan aplikasi tidak terlihat seperti hang.
            
            - Transaction Management
                * Programatic
                * Declarative : pakai annotatio di method.
                * Distributed transaction : bisa melakukan management transaction lintas merek database.
                * Transaction Popagation : bisa memecah transaction dan mengkoordinaksikan transkaction tersebut.
                    - REQUIRED : Jika belum ada transaction buat, jika sudah ada maka join.
                    - REQUIRES NEW : Jika belum ada login buat transaction, jika sudah ada maka transaction sebelumnya akan dipause, dan membuat transaction baru, dan akan commit sendiri kemudian melanjutkan transaction yang pertama.

            >> SPRING DATA JPA

                Fitur : 
                    - Tidak perlu menulis kode program untuk CURD.
                    - Query method : membuat method yang otomatis jadi query
                    - pagination
                    - integrasi dengan aplikasi web untuk konversi id ke object dan mengenari parameter untuk paging dan sorting.8

## IMPLEMENTASI AKSES DATABASE

    Langkah-langkah: 
        1. Tambahkan Library di 'pom.xml'
            >> Tidak perlu menyertakan verison pada tiap library, karena sudah dihandle oleh spring.

            * Database driver
                - mysql:mysql-connector-java

            * Migration 
                - org.fylwaydb:fylway-core

            * Spring Data JPA
                -org.springframework.boot:spring-boot-starter-data-jpa

        2. Buat migration script
            - buat folder didalam direktori resources/db/migration
            - buat file sesuai dengan nama yang sudah ditentukan sehingga dapat dideteksi dan dibaca oleh framework spring. (V1.0.0.2021082501__Skema_Kelurahan.sql)
            - isikan dengan perintah SQL creawte table kelurahan
                '''
                    create table kelurahan(
                        id varchar(36) primary key,
                        kode varhchar(10) unique,
                        nama varchar(255) not null,
                        kodepos varvhar(10) not null
                    );
                '''

        3. Buat konfigurasi koneksi database
            - buat konfigurasi dengan membuat variable dan value di file application.properties
                ...
                    spring.datasource.url=jdbc:mysql://localhost/test
                    spring.datasource.username=kemendag
                    spring.datasource.password=1xPaXnk8dBrV3JxNa4dj
                ...

            >> jalankan docker container
                $ docker run --rm --name kemendag-db -v "f:/JavaProjects/spring-kemendag-2018/kemendagdb:/var/lib/mysql" -e MYSQL_DATABASE=kemendagdb -e MYSQL_USER=kemendag -e MYSQL_PASSWORD=1xPaXnk8dBrV3JxNa4dj -e MYSQL_ROOT_PASSWORD=1xPaXnk8dBrV3JxNa4dj -p 8080:8080 mysql:8.0

            >> cara akses ke database mysql docker.
                - docker exec -ti kemendag-db bash
                - # mysql -u kemendag -p
                - # 1xPaXnk8dBrV3JxNa4dj
            

        4. Buat entity mapping class dengan table

        5. buat DAO

        6. Gunakan di aplikasi web