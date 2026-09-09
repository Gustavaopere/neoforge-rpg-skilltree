# AeroStar

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c769db9f0db81a9bdf5eb41140c89b8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AeroStar
- **Arquivo JAR:** `AeroStar-1.0.1.jar`
- **Versão 1.21.1:** `1.0.1`
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê completo, porém compatibilidade runtime com Northstar Redux 0.6.4 permanece FAIL-CLOSED até conferir SHA256 do JAR físico/patch específico.
- **Categoria:** Tecnologia; Compat
- **Compatibilidade/Riscos:** BLOQUEIO ATUAL: patch notes registram NoClassDefFoundError no AeroStar 1.0.1 original com Northstar Redux 0.6.4 por mudança de NorthstarDimensions/API. A modlist ainda mostra `AeroStar-1.0.1.jar`; isso não prova se o binário foi patched e renomeado. Conferir SHA256 físico contra original 12d6ce... e patched 285992... antes de aprovar runtime. Continua incompatível com o antigo Northstar–Aeronautics Compatibility em paralelo.
- **Decisão:** vazio
- **Dependências:** Obrigatórias upstream: Create, Create Aeronautics e Northstar. Pack atual: Create 6.0.10, Aeronautics 1.3.2 (bundle) e Northstar Redux 0.6.4+1.21.1 presentes.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aerostar
- **Função:** Bridge Create Aeronautics ↔ Northstar Redux: transfere physics ships entre dimensões/planetas com Dimensional Drive, preservando block entities, passageiros, assentos e momentum; inclui Orbital Physics Assembler e ferramentas de navegação/overlay.
- **Histórico da decisão:** vazio
- **Observações:** Dossiê completo documentalmente. PENDÊNCIA operacional: calcular SHA256 do JAR instalado; se original, substituir pelo `AeroStar-1.0.1-PATCHED-Northstar-0.6.4.jar`; depois executar full-pack/dedicated-server smoke e transferência dimensional.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial AeroStar + PATCH-NOTES-AeroStar-Northstar-0.6.4.txt de 08/09/2026.
- **Sobreposição:** Substitui o antigo compatibility mod Northstar↔Aeronautics. Não substitui Northstar Redux, Aeronautics ou Sable. Nenhum segundo top-level do compat antigo foi encontrado na modlist atual.
- **Data da última decisão:** 2026-08-27

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
