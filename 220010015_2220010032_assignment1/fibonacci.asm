	.data
n:
	10
	.text
main:
	load %x0, $n, %x3
	addi %x0, 0, %x4 ;a
	addi %x0, 1, %x5 ;b
	addi %x0, 0, %x6 ;numofStored
	addi %x0, 65535, %x10 ;lastaddress
loop:
	store %x4, 0, %x10
	subi %x10, 1, %x10
	addi %x6, 1, %x6
	beq %x3, %x6, exit
	store %x5, 0, %x10
	addi %x6, 1, %x6
	subi %x10, 1, %x10
	beq %x3, %x6, exit
	add %x4, %x5, %x4
	add %x5, %x4, %x5
	jmp loop
exit:
	end