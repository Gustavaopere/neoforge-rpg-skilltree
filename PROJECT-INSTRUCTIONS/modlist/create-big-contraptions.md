# Create: Big Contraptions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81edb2dec7a034001830
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Big Contraptions
- **Arquivo JAR:** `bigcontraptions-neoforge-1.0.jar`
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Performance, QoL
- **Função:** Patch client-side independente que contorna MC-185901 elevando o limite aceito de playerdata de ~2 MB para ~10 MB, criado para permitir receber/renderizar players com contraptions/dados muito grandes.
- **Dependências:** Não depende de Create segundo o projeto oficial; client-side NeoForge 1.21.1. O nome Create: Big Contraptions descreve o caso de uso, não uma integração técnica obrigatória.
- **Sobreposição:** Utilidade específica para payload/playerdata grande. Não aumenta tamanho físico de contraptions, não altera Create contraption limits e não substitui otimizações gerais de rede.
- **Compatibilidade/Riscos:** Altera limite de payload/playerdata no cliente. Riscos: memória/alocação maiores, disconnect se outro limite/proxy/server intervier, confusão com mods que alteram networking e tratar o patch como aumento de tamanho de contraption. Não muda cinética Create.
- **Observações:** Projeto declara explicitamente: NÃO é addon de Create e NÃO requer Create. Port client-side inspirado na função equivalente do XLPackets antigo; limite documentado passa de cerca de 2 MB para 10 MB.
- **Procedência:** modlist.txt física atual de 08/09/2026 + metadata runtime + CurseForge/Modrinth oficiais Create: Big Contraptions 1.0 + referência oficial ao bug Mojang MC-185901.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-big-contraptions
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Create: Big Contraptions 1.0 físico/release confirmado; escopo real de playerdata/payload client-side preservado e independência técnica de Create mantida. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 09/09/2026, o JAR `bigcontraptions-neoforge-1.0.jar` foi revalidado e permaneceu classificado como patch client-side independente para playerdata/payload grande, sem converter o nome Create em dependência técnica ou decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ⚠️ Versão física confirmada: `bigcontraptions-neoforge-1.0.jar`, mod id `bigcontraptions`, runtime `1.0`. Apesar do nome editorial **Create: Big Contraptions**, o projeto oficial declara explicitamente que **não é addon de Create e não requer Create**.

## 1. Papel real
Create: Big Contraptions é um **patch client-side para tamanho de playerdata/payload**, criado porque contraptions muito grandes podem aumentar o volume de dados associados ao jogador a ponto de atingir o limite vanilla do cliente.
Ele não registra máquinas, blocks, kinetic components, contraption types ou progressão Create.

## 2. Problema MC-185901
O projeto referencia o bug Mojang **MC-185901**, relacionado ao limite de dados recebidos de jogador. A finalidade do mod é permitir que o cliente aceite playerdata maior sem desconectar/falhar no caso específico que motivou o projeto.
Esse é um patch de transporte/aceitação de dados, não de semântica do conteúdo carregado.

## 3. Limite documentado
Segundo a documentação oficial:
- limite vanilla relevante: aproximadamente **2 MB**;
- limite com o mod: aproximadamente **10 MB**.
Esses números descrevem o teto aceito pelo patch. Não significam que todo jogador passe a consumir 10 MB nem que o mod aloque 10 MB permanentemente.

## 4. Relação com contraptions
O projeto nasceu para resolver casos em que players carregando/renderizando contraptions grandes excedem o limite. Isso não significa que o mod:
- aumente max blocks de uma contraption;
- altere assembly;
- altere stress/speed;
- mude physics;
- permita contraptions que Create rejeitaria por outra razão.
Ele apenas evita que **playerdata grande** seja rejeitado pelo limite que corrige.

## 5. Independência de Create
O projeto declara que Create não é dependência. Consequências operacionais:
- não marcar `Create` como hard dependency;
- não usar API Create em integração própria com este mod por inferência do nome;
- o patch pode ser útil para outros casos de playerdata grande, ainda que seu caso de uso original seja Create.

## 6. Origem conceitual
A documentação informa que a solução é portada de funcionalidade equivalente do antigo **XLPackets** para versões mais antigas, com a diferença de que este projeto é focado/client-side para o problema atual.
Isso não torna XLPackets uma dependência nem garante equivalência integral de implementação.

## 7. Client/server
O projeto é client-side. Portanto:
- servidor continua authority do conteúdo do playerdata;
- o cliente apenas aceita/processa um payload maior;
- instalar o mod não autoriza cliente a enviar state arbitrário maior ao servidor;
- código server-side próprio não deve depender desta library/patch.

## 8. Networking e segurança operacional
Aumentar um limite remove uma barreira de tamanho, mas não valida conteúdo. Regras:
- payload ainda precisa ser estruturalmente válido;
- outros limites de protocolo/proxy/server podem continuar existindo;
- dados grandes aumentam custo de alocação, parsing e renderização;
- falha deve resultar em disconnect/error controlado, não state parcial do player.

## 9. Lifecycle
Testar especialmente:
- primeiro login com playerdata grande;
- reconnect;
- troca de dimensão;
- respawn;
- player entrando na tracking range de outro player com dados grandes;
- unload/reload de contraption associada ao caso de uso;
- servidor enviando dados abaixo e acima do antigo teto.

## 10. Relação com performance
O mod não é uma otimização geral. Permitir payload maior pode inclusive elevar custo de memória/processamento em casos extremos. Categoria Performance/QoL significa que corrige uma limitação técnica de carregamento, não que aumente FPS.

## 11. Riscos
1. Alocações maiores em clients com pouca memória.
2. Outro componente de networking manter limite menor e continuar desconectando.
3. Dados malformados maiores aumentarem custo antes da falha.
4. Usuário confundir o patch com aumento de contraption size.
5. Diagnóstico errado de lag como problema do limite de payload.
6. Dependência Create inventada por causa do nome.

## 12. Matriz de testes
1. Client sem Create, em ambiente controlado, deve respeitar a declaração de independência técnica.
2. Login normal com playerdata pequeno: nenhuma regressão.
3. Caso >2 MB e <10 MB: confirmar que o cliente não falha pelo limite antigo.
4. Caso acima do novo teto: falha controlada/diagnosticável.
5. Multiplayer com outro jogador portando contraption grande.
6. Reconnect/dimension change sem duplicar state.
7. Monitorar memory allocation/GC em payloads grandes.
8. Confirmar que assembly/stress/contraption rules de Create não mudam.

## 13. Evidência
- modlist física atual: `bigcontraptions-neoforge-1.0.jar`;
- CurseForge/Modrinth oficiais do projeto;
- declaração oficial de que não é addon de Create e não possui dependência Create;
- documentação do patch MC-185901 e do aumento aproximado de 2 MB para 10 MB;
- referência oficial ao caso de uso de contraptions grandes e à origem em XLPackets antigo.

> 📦 Escopo canônico: **aceitar playerdata maior no cliente**. Nenhum conteúdo, cinética ou limite de construção Create foi atribuído ao mod.