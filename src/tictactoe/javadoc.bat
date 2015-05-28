
del \Saddleback\Website\cs4b\doc\*.*
"C:\Program Files\Java\jdk1.7.0_21\bin\javadoc.exe" -overview overview.html -d \Saddleback\Website\cs4b\TicTacToe\doc *.java
del TicTacToe.zip
"C:\Program Files\7-zip\7z.exe" a -tzip TicTacToe.zip Player.java Board.java Coordinate.java TicTacToe.java Message.java MessageTemplate.java GoForIt.java PerformanceRating.java VitalStatistics.java
"C:\Program Files\7-zip\7z.exe" a -tzip TicTacToe.zip  overview.html javadoc.bat
"C:\Program Files\7-zip\7z.exe" t TicTacToe.zip
copy TicTacToe.zip \Saddleback\Website\cs4b\TicTacToe

