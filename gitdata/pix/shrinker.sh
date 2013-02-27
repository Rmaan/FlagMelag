for pic in `find .`; do
	convert $pic -resize 50% $pic
done
