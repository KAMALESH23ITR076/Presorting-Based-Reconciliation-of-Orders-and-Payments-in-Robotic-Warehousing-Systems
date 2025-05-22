def find_unmatched_orders_and_payments(orders, payments):
    orders.sort()
    payments.sort()
    
    unpaid_orders = []
    extra_payments = []
    
    i = j = 0
    
    while i < len(orders) and j < len(payments):
        if orders[i] == payments[j]:
            i += 1
            j += 1
        elif orders[i] < payments[j]:
            unpaid_orders.append(orders[i])
            i += 1
        else:
            extra_payments.append(payments[j])
            j += 1
    
    while i < len(orders):
        unpaid_orders.append(orders[i])
        i += 1
    
    while j < len(payments):
        extra_payments.append(payments[j])
        j += 1
    
    return unpaid_orders, extra_payments


orders = input("Orders: ").split()
payments = input("Payments : ").split()

unpaid, extra = find_unmatched_orders_and_payments(orders, payments)

print("Unpaid Orders:", unpaid)    
print("Extra Payments:", extra)       

