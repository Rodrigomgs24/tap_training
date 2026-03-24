# Semana 1 — Resumo: Introdução à Programação Funcional em Scala

---

## 1. Porquê Programação Funcional?

A programação **imperativa** (Java, PHP, C) foi construída à imagem da arquitetura von Neumann: variáveis mutáveis, loops, assignments. Funciona bem num CPU único.

O problema surge com **paralelismo**. Quando múltiplas threads acedem à mesma variável mutável ao mesmo tempo, temos uma **data race**.

### Data Race
- Ocorre quando duas threads acedem à mesma memória e pelo menos uma delas escreve
- É **não-determinística**: o bug só aparece quando o timing é exacto, o que pode nunca acontecer nos testes mas acontecer em produção
- Exemplos reais: Therac-25 (mortes por radiação), Northeast Blackout 2003

### A solução funcional
Sem variáveis mutáveis → não há nada para as threads disputarem → não há data races.

---

## 2. O que é Programação Funcional?

**Sentido restrito:** programar sem variáveis mutáveis, sem assignments, sem loops, sem estruturas de controlo imperativas.

**Sentido amplo:** focar no que queremos transformar, não em como mudar estado.

| | Imperativo | Funcional |
|---|---|---|
| Foco | Como fazer (algoritmos + estado) | O quê transformar |
| Estado | Muda constantemente | Não existe |
| Iteração | Loops | Recursão |
| Unidade principal | Instâncias de classes | Funções como valores |

### Funções como First-Class Citizens
Em FP, funções são valores como qualquer outro. Podem ser:
- Passadas como argumentos a outras funções
- Devolvidas como resultado de funções
- Guardadas em variáveis

---

## 3. `val`, `lazy val` e `def`

```scala
val x = 5 + 3         // calculado imediatamente, resultado guardado (imutável)
lazy val y = 5 + 3    // calculado apenas quando usado pela primeira vez
def z = 5 + 3         // função: calculada de novo cada vez que é chamada
```

### Exemplo prático com random:
```scala
val vtest = util.Random.nextInt    // calculado uma vez → sempre o mesmo número
def dtest = util.Random.nextInt    // calculado cada vez → número diferente
lazy val lvtest = util.Random.nextInt  // calculado na primeira chamada → depois igual ao val
```

Em FP sem mutação, `val` é o mais usado para guardar valores. Reflecte **immutability**.

---

## 4. Funções como Valores

```scala
val veven: Int => Boolean = (_ % 2 == 0)
// tipo: Int => Boolean  →  recebe Int, devolve Boolean
// _  →  atalho para o argumento

val lessThan: (Int, Int) => Boolean = (a, b) => a < b
// recebe dois Int, devolve Boolean
// compara se o primeiro é menor que o segundo
```

---

## 5. Substitution Model e Side Effects

O **substitution model** é como Scala avalia expressões: substitui nomes pelos seus valores e operadores pelas operações, até chegar a um valor final.

```
b * b - 4 * a * c     (a=2, b=4, c=1)
4 * b - 4 * a * c
4 * 4 - 4 * a * c
16 - 4 * a * c
...
8
```

Isto só funciona **sem side effects**.

### Side Effects
Uma função tem side effect quando faz algo além de devolver um valor:
- Modificar uma variável
- Modificar uma estrutura de dados
- Escrever num ficheiro
- Imprimir no ecrã (`println`)
- Ler input do utilizador
- Lançar uma excepção

### Referential Transparency
Uma função sem side effects chamada com os mesmos argumentos devolve **sempre o mesmo resultado**. Chama-se **referential transparency** e é o que torna FP fácil de testar e raciocinar.

---

## 6. Sintaxe Scala Básica

```scala
// object = singleton (classe + única instância)
object MyModule:

  // private = só acessível dentro do object
  private def abs(n: Int): Int =
    if (n < 0) -n else n     // sem return explícito — devolve a última expressão

  def formatAbs(x: Int) =
    val msg = "The absolute value of %d is %d"
    msg.format(x, abs(x))
```

- `Unit` = equivalente a `void` em Java (função sem retorno útil)
- `if/else` em Scala é uma **expressão** que devolve um valor, não uma instrução
- A função devolve a **última expressão** avaliada

---

## 7. Tail Recursion

Em FP não há loops. A iteração faz-se com **recursão**. Mas recursão normal acumula stack frames → stack overflow.

### Recursão normal (não tail recursive)
```scala
def fibo(n: Int): Int =
  if (n < 2) 1 else fibo(n - 2) + fibo(n - 1)
```
Não é tail recursive porque a última operação é a **soma**, não a chamada recursiva. A stack tem de guardar os resultados intermédios.

### Tail Recursion
Se a chamada recursiva for a **última coisa** que a função faz, o compilador reutiliza o mesmo stack frame → sem stack overflow.

```scala
def tailFibo(n: Int) =
  @annotation.tailrec
  def fibo(i: Int, a: Int, b: Int): Int =
    if (i < 2) b else fibo(i - 1, b, a + b)
  fibo(n, 1, 1)
```

- `a` e `b` são os **dois últimos valores da sequência de Fibonacci**
- O estado que normalmente estaria na stack é passado nos **parâmetros**
- `@annotation.tailrec` garante que o compilador dá erro se a função não for tail recursive

### Trace de execução:
```
fibo(4, 1, 1)  →  i=4, a=1, b=1
fibo(3, 1, 2)  →  i=3, a=1, b=2
fibo(2, 2, 3)  →  i=2, a=2, b=3
fibo(1, 3, 5)  →  i=1  →  devolve b = 5
```

---

## Conceitos-chave desta semana

| Conceito | Definição curta |
|---|---|
| **Immutability** | Um valor definido uma vez nunca muda |
| **Side effect** | Qualquer coisa que uma função faz além de devolver um valor |
| **Referential transparency** | Mesmos inputs → mesmo output, sempre |
| **Substitution model** | Avaliar expressões substituindo nomes por valores |
| **First-class function** | Função que pode ser passada, devolvida e guardada como valor |
| **Tail recursion** | Recursão onde a chamada recursiva é a última operação |