# 14.04 — Preview fiel e ciclo de revisão

## Regra principal

O preview é uma **renderização do VoxelModel**, não uma ilustração inspirada nele.

## Saídas

O pipeline deve suportar ao menos:

- preview 3D/voxel local ou web;
- projeções isométricas e ortogonais úteis à revisão;
- manifest com dimensões, número de blocos e hash;
- export `.schem` para viewers compatíveis, como parte do mesmo ciclo.

A tecnologia de renderização pode mudar sem alterar o formato canônico.

## Revisão

Cada artefato de revisão exibe:

- `buildingId`;
- nível;
- revision/hash;
- paleta/provider;
- dimensões;
- warnings de blocos ausentes;
- BOM resumido.

Assim a aprovação humana referencia uma revisão inequívoca.

## Paridade

Após export, reimportar quando possível e comparar voxel hash normalizado. Diferenças de formato que não alterem block state podem ser normalizadas; diferença física é falha.

## Testes

- imagem/mesh contém somente voxels do modelo;
- transparência/ar não cria blocos extras;
- orientation de blocos direcionais preservada;
- preview e `.schem` registram mesmo revision hash;
- mudança de um bloco invalida aprovação anterior.

## Acceptance

Não é possível aprovar visualmente uma versão e entregar schematic de outra sem o gate detectar a divergência.