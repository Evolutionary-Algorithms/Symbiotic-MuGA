echo off
echo ################################################################
echo #          M U G A - Multiset Genetic Algorith                 #
echo #                                                              #
echo #          (c) Antonio Manso  manso@ipt.pt                     #
echo #          (c) Luis Correia   Luis.Correia@di.fc.ul.pt         #
echo #                                                        2013  #
echo ################################################################
echo .
echo  running MuGA  
echo .
cd dist
ren *.jar muga.jar
cd..
echo .
java -jar dist/muga.jar
echo . simulation completed!
pause
