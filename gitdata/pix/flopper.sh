files=`find .`
for pic in $files; do
	name=`echo $pic | cut -d. -f2`
	convert $pic -flop "./$name-mirror.png"
done
