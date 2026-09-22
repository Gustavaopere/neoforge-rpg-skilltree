# AeroStar

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#15**: `AeroStar-1.0.1.jar`, mod id `aerostarcomp`, runtime `1.0.1`, SHA-1 `7e5ae54b22be453dc9ff0a35111d44ae78bd2128`. O stack físico atual usa Northstar Redux `0.6.5+1.21.1`; como os fingerprints de referência original/patched registrados no dossiê são SHA-256 e a modlist fornece apenas SHA-1, a identidade do binário AeroStar continua sem resolução e a certificação permanece **FAIL-CLOSED**.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 16/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AeroStar
- **Arquivo JAR:** `AeroStar-1.0.1.jar`
- **Versão 1.21.1:** `1.0.1`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — ownership de state transfer, lifecycle, multiplayer, fingerprint SHA-1 e matriz de validação aprofundados. BLOQUEIO FAIL-CLOSED Northstar Redux 0.6.4 permanece até SHA-256 físico do JAR confirmar original vs patched.
- **Categoria:** Tecnologia; Compat
- **Compatibilidade/Riscos:** BLOQUEIO ATUAL: patch notes registram NoClassDefFoundError no AeroStar 1.0.1 original com Northstar Redux 0.6.4 por mudança de NorthstarDimensions/API. A modlist ainda mostra `AeroStar-1.0.1.jar`; isso não prova se o binário foi patched e renomeado. Conferir SHA256 físico contra original 12d6ce... e patched 285992... antes de aprovar runtime. Continua incompatível com o antigo Northstar–Aeronautics Compatibility em paralelo.
- **Decisão:** vazio
- **Dependências:** Obrigatórias upstream: Create, Create Aeronautics e Northstar. Pack atual: Create 6.0.10, Aeronautics 1.3.2 (bundle) e Northstar Redux 0.6.4+1.21.1 presentes.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aerostar
- **Função:** Bridge Create Aeronautics ↔ Northstar Redux: transfere physics ships entre dimensões/planetas com Dimensional Drive, preservando block entities, passageiros, assentos e momentum; inclui Orbital Physics Assembler e ferramentas de navegação/overlay.
- **Histórico da decisão:** vazio
- **Observações:** Dossiê documental completo, mas runtime AeroStar↔Northstar Redux 0.6.4 não aprovado. Calcular SHA-256 do JAR instalado e comparar com os fingerprints original/patched já registrados; depois executar smoke full-pack/dedicated e transferências dimensionais.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + CurseForge oficial AeroStar 1.0.1 + patch notes já auditadas. Artefato `AeroStar-1.0.1.jar`, runtime `1.0.1`, SHA-1 `7e5ae54b22be453dc9ff0a35111d44ae78bd2128`, mixin `aerostarcomp.mixins.json`. O SHA-1 físico não substitui a comparação SHA-256 original/patched.
- **Sobreposição:** Substitui o antigo compatibility mod Northstar↔Aeronautics. Não substitui Northstar Redux, Aeronautics ou Sable. Nenhum segundo top-level do compat antigo foi encontrado na modlist atual.
- **Data da última decisão:** 2026-08-27
- **Estado no pack:** Integrado ao Github

## Escopo e papel
Compatibilidade espacial entre **Create Aeronautics** e **Northstar Redux**, construída como continuação corrigida e expandida do antigo Northstar–Aeronautics Compatibility. O núcleo é o `Dimensional Drive`: o ship físico é serializado, recriado na dimensão de destino e restaurado com block entities, passageiros, assentos e momentum.

## Runtime e autoridade
- JAR físico: `AeroStar-1.0.1.jar`.
- Mod ID: `aerostarcomp`.
- Runtime: `1.0.1`.
- Release oficial NeoForge 1.21.1 confirmada.

## Dependências
- **Obrigatórias upstream:** Create, Create Aeronautics e Northstar.
- Pack atual: Create `6.0.10`, Aeronautics bundle `1.3.2` e Northstar Redux `0.6.4+1.21.1` presentes.
- Não foi encontrado outro top-level do antigo compatibility mod na modlist atual.

## Mecânicas relevantes
O drive usa Star Map para selecionar destino; planetas exigem Interplanetary Navigator, enquanto Earth orbit pode ser selecionada sem mapa. O mod inclui também **Orbital Physics Assembler**, permitindo montagem/desmontagem em órbita, e overlay de goggles/helmet com destino, velocidade e altitude de lançamento. O comando `/drivetransfer <planet>` existe como ferramenta administrativa de teste.

## Compatibilidade, sobreposição e riscos
O upstream ordena explicitamente usar AeroStar **em vez do compat original**, não em conjunto. Isso é risco de incompatibilidade confirmado, não hipótese. O fluxo é altamente sensível à persistência de block entities, passageiros, assentos, inventários e cinética durante troca de dimensão; qualquer addon que guarde estado fora do sublevel precisa de teste real.

## Limites
AeroStar não substitui Northstar, Aeronautics ou Sable. Não é outro sistema espacial completo: é a bridge que permite levar physics ships do Aeronautics às dimensões/progressão do Northstar e operar estações físicas em órbita.


## Ownership técnico e superfícies de estado
- **Ownership primário:** transferência dimensional de physics ships entre a progressão/dimensões do Northstar Redux e o runtime Aeronautics/Sable; inclui `Dimensional Drive`, `Orbital Physics Assembler`, navegação e overlay.
- **Mod ID físico:** `aerostarcomp`.
- **Mixin config físico:** `aerostarcomp.mixins.json`.
- A bridge precisa preservar estado que não pode ser reduzido a blocos estáticos: block entities, passageiros, seats, momentum, inventários, cinética, fluids e qualquer estado externo mantido por addons.
## Configuração, comandos e dados
O comando `/drivetransfer <planet>` está documentado como ferramenta administrativa de teste. Star Map e Interplanetary Navigator participam da seleção de destino. Não foi inventariada neste lote uma lista completa de arquivos/chaves de configuração próprios; qualquer ajuste adicional deve permanecer fail-closed até leitura direta do artefato/config gerado.
## Client/server, lifecycle e multiplayer
A decisão e execução da transferência dimensional devem ser server-authoritative; overlays/goggles/helmet constituem superfície de apresentação no cliente. O lifecycle crítico é **ship montado → destino selecionado → serialização/transferência → recriação no destino → ressincronização de passageiros e estado → save/reload → reconexão**. Em dedicated server, também é obrigatório validar ocupantes desconectando/reconectando e múltiplos passageiros.
## Fingerprint físico e bloqueio de binário
- JAR físico: `AeroStar-1.0.1.jar`
- Runtime: `1.0.1`
- SHA-1 do artefato instalado: `7e5ae54b22be453dc9ff0a35111d44ae78bd2128`
- Mixin config: `aerostarcomp.mixins.json`
- O SHA-1 acima **não resolve** a pendência já documentada porque os fingerprints de referência original/patched disponíveis são SHA-256. Até calcular SHA-256 do JAR físico e comparar com os valores documentados, o status de compatibilidade com Northstar Redux 0.6.4 permanece fail-closed.


## Reconciliação física atual — 18/09/2026
A modlist física mais recente (`modlist(1).txt`, 16/09/2026) mantém `AeroStar-1.0.1.jar`, mas Northstar Redux avançou de `0.6.4+1.21.1` para `0.6.5+1.21.1`. O patch documentado para Northstar 0.6.4 não prova compatibilidade com 0.6.5; portanto o gate operacional AeroStar ↔ Northstar permanece **FAIL-CLOSED** até validação do binário/runtime atual.

## Testes recomendados
1. Transferir ship mínimo Earth ↔ orbit e validar posição/momentum.
2. Repetir com inventários, tanks, cinética Create, block entities e assentos ocupados.
3. Testar Moon/Mars/Venus/Mercury com Star Map + Navigator.
4. Validar Orbital Physics Assembler em órbita.
5. Save/reload antes e depois da transferência; testar dedicated server e reconexão de passageiros.
6. Confirmar ausência do compat original no runtime para evitar dupla implementação.

## Evidências
- [CurseForge oficial — Create: AeroStar Northstar Comp](https://www.curseforge.com/minecraft/mc-mods/create-aerostar)
- Modlist física atual e guia consolidado de Tecnologia.

## Bloqueio atual — Northstar Redux 0.6.4

> ⚠️ **FAIL-CLOSED.** As patch notes do projeto registram que o AeroStar 1.0.1 original foi compilado contra a API pre-0.6 do Northstar e pode falhar com `NoClassDefFoundError` em `NorthstarDimensions` quando usado com Northstar Redux 0.6.4. O patch específico reescreve `OrbitGravitySystem.class` e `DriveTransferCommand.class` para a API 0.6.4.

A modlist física mais recente ainda mostra o top-level `AeroStar-1.0.1.jar`, enquanto as patch notes instruem remover esse artefato e instalar `AeroStar-1.0.1-PATCHED-Northstar-0.6.4.jar`.
Isso **não prova** que o binário atual está sem patch, porque um JAR patched poderia ter sido renomeado. Porém o filename físico não comprova a aplicação do patch e, nesta etapa, o SHA256 do JAR físico não foi lido.

### Evidência do patch
- SHA256 original documentado: `12d6ce716864896f78cf018d06e8b563d0270a8755d8fd90676d340a92cedc39`.
- SHA256 patched documentado: `285992bdc84f7938d1e9b8c33d196d94128303c095c9d2645fa386fe5e1666d5`.
- Northstar 0.6.4 usado na validação estática do patch: SHA256 `446684adabeefa223c4d7b3704ea8c1b5c6810f421c0f7b8f1fe45fad14c21c2`.

### Pendência operacional
- [ ] Calcular SHA256 do `AeroStar-1.0.1.jar` realmente instalado.
- [ ] Se bater com o original, substituir pelo patched antes de considerar AeroStar runtime-safe com Northstar Redux 0.6.4.
- [ ] Se bater com o patched, registrar que houve rename do artefato e manter a provenance.
- [ ] Depois executar full-pack/dedicated-server smoke e testar transferência dimensional.

Enquanto o hash físico não for confirmado, documentação pode ser considerada completa, mas **compatibilidade runtime AeroStar↔Northstar 0.6.4 permanece não aprovada**.
