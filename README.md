# STW

Sufiksų medis

Sufiksų medis yra labai svarbi ir naudinga daugelio programų duomenų struktūra. Tradiciniai 
tokio medžio konstravimo algoritmai labai priklauso nuo to, ar visos priesagos yra įterptos, kad 
būtų gautos efektyvios laiko ribos. Todėl straipsnio tikslas buvo sukurti tokią duomenų 
struktūrą, kuri leidžia atlikti paieškos operacijas su viena prieiga prie antrinės saugyklos, 
naudojant tik O (m) pirminio saugojimo langelius, nepriklausomai nuo paieškos eilutės ilgio.

Autoriai straipsnyje nagrinėjo tris algoritmus, dviejų iš jų pagrindas buvo algoritmas 
Ukkonen‘o, o pirmas pristatytas algoritmas net ne toks efektyvus, kaip Ukkonen‘o. Tam, kad 
suprasti, kaip gi vis dėl to veikia sufiksų medis ir kodėl autoriai ieškojo efektyvesnio būdo 
sudaryti medį, buvo nuspręsta realizuoti optimizuotą Ukkonen algoritmą bei įvertinti jo laiko ir 
vietos sudėtingumus.

Šio darbo rezultatas - realizuotas Ukkonen algoritmas, kuriuo kiekvieno ciklo 
„prasukimo“ metu atliekamas darbas yra O(1), nes visos briaunos atnaujinamos automatiškai, 
didinant skaičių. Taip pat ir vieno papildomo simbolio įterpimas vyksta tik per O (1) laiką. 
Kadangi, mūsų ciklą praeina kiekvienas eilutės simbolis, algoritmas veikia tiesiniu laiku ir, 
galima manyti, kad n sudėtingumo medžiui sukurti reikia tik O (n) laiko ir O (m) vietos.

![image](https://user-images.githubusercontent.com/45011652/234404754-a1c993a1-6f03-4792-9c50-72fec65545c6.png)


Suffix Trees on Words

The suffix tree is a very important and useful data structure for many applications. 
Traditional suffix tree construction algorithms rely heavily on the fact that all suffixes are 
inserted, in order to obtain efficient time bounds. Construction of a word suffix tree is 
nontrivial, in particular when only O(m) construction space is allowed.

The authors analyzed three algorithms in the article, two of them were based on an algorithm
Ukkonen's, and the first presented algorithm is not even as efficient as Ukkonen's. In order to
to understand how the suffix tree works anyway and why the authors looked for a more efficient way
to create a tree, it was decided to implement the optimized Ukkonen algorithm and evaluate its time and
local complexities.

The result of this work is the realization of Ukkonen's algorithm, which in each cycle
the work done during a "twist" is O(1) because all edges are updated automatically,
increasing the number. Also, inserting one extra character takes only O(1) time.
Since our loop goes through each character of the string, the algorithm runs in linear time and,
it can be assumed that building a tree of complexity n takes only O(n) time and O(m) space.

![image](https://user-images.githubusercontent.com/45011652/234404754-a1c993a1-6f03-4792-9c50-72fec65545c6.png)
