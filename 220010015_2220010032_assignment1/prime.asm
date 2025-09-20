	.data
a:
	50
	.text
main:
	load %x0, $a, %x3 ;givennum
	blt %x3, 2, no ;ifgivenlt2
	beq %x3, 2, yes ;ifgiven2
	addi %x0, 2, %x4 ;
loop:
	div %x3, %x4, %x9
	beq %x31, 0, no
	addi %x4, 1, %x4
	beq %x3, %x4, yes
	jmp loop
no:
	subi %x0, 1, %x10
	end
yes:
	addi %x0, 1, %x10
	end