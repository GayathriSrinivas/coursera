
def multiply(x,y,n):

	if(n >1):
		a = x / pow(10,n/2)	
		b = x % pow(10,n/2)
		c = y / pow(10,n/2)
		d = y % pow(10,n/2)
		print "a::",a
		print "b::",b
		print "c::",c
		print "d::",d

		ac =  multiply(a,c,n/2) 
		ad =  multiply(a,d,n/2) 
		bc =  multiply(b,c,n/2) 
		bd =  multiply(b,d,n/2)

		return (pow(10,n) * ac) + (( ad + bc) * pow(10,n/2)) + bd
	else:
		return x * y

def main():
	x = 123  
	y = 567
	n = 4
	print multiply(x,y,n)
	print x*y

if __name__ == "__main__":
	main()