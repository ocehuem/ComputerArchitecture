	.data
a:
    70
    80
    40
    20
    10
    30
    50
    60
n:
    8
    .text
main:
    load %x0, $n, %x3
    add %x0, %x0, %x4  ;i 
    subi %x3, 1, %x5   ;n-1
outerloop:
    beq %x4, %x5, done
    addi %x4, 1, %x4
    addi %x0, 0, %x8 ;j
innerloop:
    beq %x8, %x5, outerloop
    addi %x8, 1, %x9 ;j, j+1
    load %x8, $a, %x6 ;x6 has value of x8 a[j]
    load %x9, $a, %x12 ;x12 has value of x9 a[j+1]
    addi %x8, 0, %x14 ;x14 has stored value of j
    addi %x8, 1, %x8 ;j+1 increment
    blt %x6, %x12, swap
    jmp innerloop
swap:
    store %x12, $a, %x14
    store %x6, $a, %x9
    jmp innerloop
done:
    end