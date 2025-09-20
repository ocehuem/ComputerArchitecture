	.data
a:
	141
	.text
main:
	load %x0, $a, %x3
	add %x3, %x0, %x6
	addi %x0, 0, %x4
loop:
	divi %x6, 10, %x5
	muli %x4, 10, %x4
	add %x31, %x4, %x4
	divi %x6, 10, %x6
	beq %x4, %x3, yes
	bne %x5, 0, loop
	beq %x5, 0, no
yes:
	addi %x0, 1, %x10 
	end
no:
	subi %x0, 1, %x10
	end