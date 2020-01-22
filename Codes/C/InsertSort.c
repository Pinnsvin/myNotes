#include <stdio.h>

int main(){
    int size = 9;
    //scanf("Please input int array of size:%d\n", &size);
    int arr[size];
    printf("%s\n", "Please input numbers:");
    for (int i = 0; i < size; i++) {
        scanf("%d\n", &arr[i]);
    }
    //insertSort
    int i,j,key;
    for (i = 1; i < size; i++) {
        key = arr[i];
        j=i-1;
        while (j>=0 && arr[j]>key) {
            arr[j+1]=arr[j];
            j--;
        }
        arr[j+1]=key;
    }
    //traverse array
    printf("%s\n", "New array is :");
    for (int i = 0; i < size; i++) {
        printf("%d\n", arr[i]);
    }
    return 0;
}
